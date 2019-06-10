package cn.icepear.dandelion.upm.biz.service;


import cn.icepear.dandelion.upm.api.domain.entity.SysMenu;
import cn.icepear.dandelion.upm.api.domain.vo.MenuVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author rim-wood
 * @description 菜单权限管理service接口
 * @date Created on 2019-04-18.
 */
public interface SysMenuService extends IService<SysMenu> {
	/**
	 * 通过角色编号查询URL 权限
	 *
	 * @param roleId 角色ID
	 * @return 菜单列表
	 */
	List<MenuVO> getMenuByRoleId(Integer roleId);

	/**
	 * 级联删除菜单
	 *
	 * @param id 菜单ID
	 * @return 成功、失败
	 */
	boolean removeMenuById(Integer id);

	/**
	 * 更新菜单信息
	 *
	 * @param sysMenu 菜单信息
	 * @return 成功、失败
	 */
	boolean updateMenuById(SysMenu sysMenu);
}
