package cn.icepear.dandelion.upm.biz.service;


import cn.icepear.dandelion.upm.api.domain.entity.SysRoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author rim-wood
 * @description 角色菜单管理service接口
 * @date Created on 2019-04-18.
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {

	/**
	 * 更新角色菜单
	 *
	 * @param role
	 * @param roleId  角色
	 * @param menuIds 菜单ID拼成的字符串，每个id之间根据逗号分隔
	 * @return
	 */
	Boolean saveRoleMenus(String role, Integer roleId, String menuIds);
}
