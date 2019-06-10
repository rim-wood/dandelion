package cn.icepear.dandelion.upm.biz.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.icepear.dandelion.upm.api.domain.entity.SysMenu;
import cn.icepear.dandelion.upm.api.domain.entity.SysRoleMenu;
import cn.icepear.dandelion.upm.api.domain.vo.MenuVO;
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

import java.util.List;

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
	@Cacheable(value = "menu_details", key = "#roleId  + '_menu'")
	public List<MenuVO> getMenuByRoleId(Integer roleId) {
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

		sysRoleMenuMapper
			.delete(Wrappers.<SysRoleMenu>query()
				.lambda().eq(SysRoleMenu::getMenuId, id));

		//删除当前菜单及其子菜单
		return this.removeById(id);
	}

	@Override
	@CacheEvict(value = "menu_details", allEntries = true)
	public boolean updateMenuById(SysMenu sysMenu) {
		return this.updateById(sysMenu);
	}
}
