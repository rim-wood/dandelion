package cn.icepear.dandelion.upm.biz.service.impl;

import cn.icepear.dandelion.common.core.constant.CommonConstants;
import cn.icepear.dandelion.common.core.utils.R;
import cn.icepear.dandelion.common.security.service.DandelionUser;
import cn.icepear.dandelion.common.security.utils.SecurityUtils;
import cn.icepear.dandelion.upm.api.domain.entity.SysRole;
import cn.icepear.dandelion.upm.api.domain.entity.SysRoleFunction;
import cn.icepear.dandelion.upm.api.domain.entity.SysRoleMenu;
import cn.icepear.dandelion.upm.api.domain.vo.RoleVO;
import cn.icepear.dandelion.upm.biz.mapper.SysRoleFunctionMapper;
import cn.icepear.dandelion.upm.biz.mapper.SysRoleMapper;
import cn.icepear.dandelion.upm.biz.mapper.SysRoleMenuMapper;
import cn.icepear.dandelion.upm.biz.service.SysRoleService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author rim-wood
 * @description 角色菜单管理service实现
 * @date Created on 2019-04-18.
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
	@Autowired
	private SysRoleMenuMapper sysRoleMenuMapper;

	@Autowired
	private SysRoleMapper sysRoleMapper;

	@Autowired
	private SysRoleFunctionMapper sysRoleFunctionMapper;

	/**
	 * 通过用户ID，查询角色信息
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public List<SysRole> listRolesByUserId(Integer userId) {
		return baseMapper.listRolesByUserId(userId);
	}

	/**
	 * 通过角色ID，删除角色,并清空角色菜单缓存
	 *
	 * @param id
	 * @return
	 */
	@Override
	@CacheEvict(value = "menu_details", allEntries = true)
	@Transactional(rollbackFor = Exception.class)
	public Boolean removeRoleById(Integer id) {
		sysRoleMenuMapper.delete(Wrappers
			.<SysRoleMenu>update().lambda()
			.eq(SysRoleMenu::getRoleId, id));
		return this.removeById(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public R<RoleVO> addRoleVO(RoleVO roleVo, Boolean isAdmin) {
		RoleVO getRoleVO = sysRoleMapper.getRoleVo(roleVo);
		DandelionUser user = SecurityUtils.getUser();
		roleVo.setDeptId(user.getGrandparentDeptId().toString());
		roleVo.setCreator(user.getRealName());
		roleVo.setCreateTime(LocalDateTime.now());
		if (getRoleVO == null){
			//保存角色对象
			sysRoleMapper.saveSysRole(roleVo);
			//保存角色对应的菜单列表
			List<SysRoleMenu> sysRoleMenus = roleVo.getMenuIdList().stream().map(menuId ->
					new SysRoleMenu(roleVo.getRoleId(), menuId)
			).collect(Collectors.toList());
			sysRoleMenuMapper.saveSysRoleMenu(sysRoleMenus);
			//保存角色对应的功能列表
			List<SysRoleFunction> sysRoleFunctions = roleVo.getFunctionIdList().stream().map(functionId ->
					new SysRoleFunction(roleVo.getRoleId(), functionId)
			).collect(Collectors.toList());
			sysRoleFunctionMapper.saveSysRoleFunction(sysRoleFunctions);
			return new R<>(CommonConstants.SUCCESS,"添加成功",roleVo);
		}else {
			if (getRoleVO.getDelFlag() == 0){
				return new R<>(CommonConstants.SUCCESS,"角色已存在，不允许重复添加",null);
			}else{
				roleVo.setRoleId(getRoleVO.getRoleId());
				return updateRoleVO(roleVo,isAdmin);
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(value = "menu_details", key = "'role-' +  #roleVo.getRoleId() + '_menu'")
	public R<RoleVO> updateRoleVO(RoleVO roleVo,Boolean isAdmin) {
		SysRole sysRole = this.getById(roleVo.getRoleId());
		if (!isAdmin && sysRole.getSysDefault().equals(1)){
			return new R<>(CommonConstants.FAIL, "权限不够，不可修改或删除系统默认角色", null);
		}
		DandelionUser user = SecurityUtils.getUser();
		roleVo.setUpdateTime(LocalDateTime.now());
		roleVo.setUpdator(user.getRealName());
		sysRoleMapper.updateRole(roleVo);
		//如果菜单列表不为空，则需要更新
		if(roleVo.getMenuIdList().size() > 0){
			//删除角色菜单信息并添加新的角色菜单信息进行更新
			sysRoleMenuMapper.deleteMenuByRoleId(roleVo.getRoleId());
			//保存角色对应的菜单列表
			List<SysRoleMenu> sysRoleMenus = roleVo.getMenuIdList().stream().map(menuId ->
					new SysRoleMenu(roleVo.getRoleId(), menuId)
			).collect(Collectors.toList());
			sysRoleMenuMapper.saveSysRoleMenu(sysRoleMenus);
		}
		//如果功能列表不为空，则需要更新
		if(roleVo.getFunctionIdList().size() > 0){
			//删除角色原有的功能信息并添加新的角色功能信息进行更新
			sysRoleFunctionMapper.deleteFunctionByRoleId(roleVo.getRoleId());
			//保存角色对应的功能列表
			List<SysRoleFunction> sysRoleFunctions = roleVo.getFunctionIdList().stream().map(functionId ->
					new SysRoleFunction(roleVo.getRoleId(), functionId)
			).collect(Collectors.toList());
			sysRoleFunctionMapper.saveSysRoleFunction(sysRoleFunctions);
		}
		return new R<>(CommonConstants.SUCCESS,"修改成功",roleVo);

	}

	@Override
	public RoleVO getSysRoleById(Integer roleId) {
		return sysRoleMapper.getSysRoleById(roleId);
	}

	@Override
	public List<SysRole> getAllList(Integer deptId) {
		return sysRoleMapper.getSysRoleByDeptId(deptId);
	}


}
