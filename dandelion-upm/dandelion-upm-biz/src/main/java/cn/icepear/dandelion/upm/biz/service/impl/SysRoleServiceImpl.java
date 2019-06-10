package cn.icepear.dandelion.upm.biz.service.impl;

import cn.icepear.dandelion.upm.api.domain.entity.SysRole;
import cn.icepear.dandelion.upm.api.domain.entity.SysRoleMenu;
import cn.icepear.dandelion.upm.biz.mapper.SysRoleMapper;
import cn.icepear.dandelion.upm.biz.mapper.SysRoleMenuMapper;
import cn.icepear.dandelion.upm.biz.service.SysRoleService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author rim-wood
 * @description 角色菜单管理service实现
 * @date Created on 2019-04-18.
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
	@Autowired
	private SysRoleMenuMapper sysRoleMenuMapper;

	/**
	 * 通过用户ID，查询角色信息
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public List listRolesByUserId(Integer userId) {
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
}
