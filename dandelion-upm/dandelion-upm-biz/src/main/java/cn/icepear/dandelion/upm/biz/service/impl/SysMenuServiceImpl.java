package cn.icepear.dandelion.upm.biz.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.icepear.dandelion.upm.api.domain.dto.RoleInfo;
import cn.icepear.dandelion.upm.api.domain.entity.SysMenu;
import cn.icepear.dandelion.upm.api.domain.entity.SysRoleMenu;
import cn.icepear.dandelion.upm.api.domain.vo.MenuVO;
import cn.icepear.dandelion.upm.api.domain.vo.SystemToMenuVo;
import cn.icepear.dandelion.upm.biz.mapper.SysMenuMapper;
import cn.icepear.dandelion.upm.biz.mapper.SysRoleMenuMapper;
import cn.icepear.dandelion.upm.biz.service.SysMenuService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author rim-wood
 * @description 菜单权限管理service实现
 * @date Created on 2019-04-18.
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
	@Autowired
	private SysRoleMenuMapper sysRoleMenuMapper;


	@Override
	@Cacheable(value = "menu_details", key = "'role-' + #roleId  + '_menu'")
	public List<MenuVO> getMenuByRoleId(Long roleId) {
		return baseMapper.listMenusByRoleId(roleId);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(value = "menu_details", allEntries = true)
	public boolean removeMenuById(Integer id) {
		// 查询父节点为当前节点的节点
		List<SysMenu> menuList = this.list(Wrappers.<SysMenu>query()
			.lambda().eq(SysMenu::getParentId, id));
		if (CollUtil.isNotEmpty(menuList)) {
			return false;
		}

		sysRoleMenuMapper.delete(Wrappers.<SysRoleMenu>query()
				.lambda().eq(SysRoleMenu::getMenuId, id));

		//删除当前菜单及其子菜单
		return this.removeById(id);
	}

	@Override
	@CacheEvict(value = "menu_details", allEntries = true)
	@Transactional(rollbackFor = Exception.class)
	public boolean updateMenuById(SysMenu sysMenu) {
		//如果修改菜单操作是修改DelFlag字段，即要删除菜单先删除其子菜单
		if(sysMenu.getDelFlag() == 1){
			List<SysMenu> sysMenus = baseMapper.sonMenuList(sysMenu.getMenuId());
			sysMenus.forEach(menu -> {
				menu.setDelFlag(1);
				//删除与该菜单的角色关联
				sysRoleMenuMapper.deleteByMenuId(menu.getMenuId());
				this.updateById(menu);
			});
			return true;
		}
		return this.updateById(sysMenu);
	}

	@Override
	public SysMenu getMenuByMenuId(Long menuId) {
		return this.getById(menuId);
	}


	/**
	 * 缓存名加 - 和systemId为了防止重复
	 */
	@Override
	@Cacheable(value = "menu_details", key = "#username  + '_menuTreeList'")
	public List<SystemToMenuVo> getMenuTreeList(String  username, List<RoleInfo> roles, String systemId,
												Boolean isAdmin) {
		for(RoleInfo roleInfo : roles) {
			if (isAdmin){
				return baseMapper.getAdminMenuTreeList(null);
			}
		}
		return baseMapper.getMenuTreeList(roles, null);
	}

	@Override
	public SysMenu selectByName(String menuName, String path) {
		return baseMapper.selectByName(menuName, path);
	}

	@Override
	@CacheEvict(value = "menu_details", allEntries = true)
	public boolean save(SysMenu sysMenu) {
		return this.retBool(Integer.valueOf(this.baseMapper.insert(sysMenu)));
	}
}
