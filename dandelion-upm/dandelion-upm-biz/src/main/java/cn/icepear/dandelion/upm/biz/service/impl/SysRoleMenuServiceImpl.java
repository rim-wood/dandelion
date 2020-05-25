package cn.icepear.dandelion.upm.biz.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.icepear.dandelion.common.core.utils.RedisUtils;
import cn.icepear.dandelion.upm.api.domain.entity.SysRoleMenu;
import cn.icepear.dandelion.upm.biz.mapper.SysRoleMenuMapper;
import cn.icepear.dandelion.upm.biz.service.SysRoleMenuService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author rim-wood
 * @description 角色菜单管理service实现
 * @date Created on 2019-04-18.
 */
@Service
@ConditionalOnBean(value = RedisUtils.class)
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {
	@Autowired
	private RedisUtils redisUtils;

	/**
	 * @param role
	 * @param roleId  角色
	 * @param menuIds 菜单ID拼成的字符串，每个id之间根据逗号分隔
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(value = "menu_details", key = "#roleId + '_menu'")
	public Boolean saveRoleMenus(String role, Long roleId, String menuIds) {
		this.remove(Wrappers.<SysRoleMenu>query().lambda()
			.eq(SysRoleMenu::getRoleId, roleId));

		if (StrUtil.isBlank(menuIds)) {
			return Boolean.TRUE;
		}
		List<SysRoleMenu> roleMenuList = Arrays
			.stream(menuIds.split(","))
			.map(menuId -> {
				SysRoleMenu roleMenu = new SysRoleMenu();
				roleMenu.setRoleId(roleId);
				roleMenu.setMenuId(Long.valueOf(menuId));
				return roleMenu;
			}).collect(Collectors.toList());

		//清空userinfo
		redisUtils.delete("user_details");
		return this.saveBatch(roleMenuList);
	}
}
