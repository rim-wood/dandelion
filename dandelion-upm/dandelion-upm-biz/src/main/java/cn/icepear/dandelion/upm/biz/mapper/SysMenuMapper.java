package cn.icepear.dandelion.upm.biz.mapper;

import cn.icepear.dandelion.upm.api.domain.dto.RoleInfo;
import cn.icepear.dandelion.upm.api.domain.entity.SysMenu;
import cn.icepear.dandelion.upm.api.domain.vo.MenuVO;
import cn.icepear.dandelion.upm.api.domain.vo.SystemToMenuVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author rim-wood
 * @description 菜单权限管理 Mapper 接口
 * @date Created on 2019-04-18.
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

	/**
	 * 通过角色编号查询菜单
	 *
	 * @param roleId 角色ID
	 * @return
	 */
	List<MenuVO> listMenusByRoleId(Long roleId);

	/**
	 * 通过角色ID查询权限
	 *
	 * @param roleIds Ids
	 * @return
	 */
	List<String> listPermissionsByRoleIds(String roleIds);

	/**
	 * 通过菜单ID查询
	 */
	SysMenu getMenuByMenuId(int menuId);


	/**
	 * 获取菜单列表
	 */
	List<SystemToMenuVo> getMenuTreeList(@Param("roles") List<RoleInfo> roles, @Param("systemId") String systemId);

	/**
	 * admin用户查所有菜单
	 */
	List<SystemToMenuVo> getAdminMenuTreeList(@Param("systemId") String systemId);

	/**
	 * 通过菜单名与路径确认菜单
	 */
	SysMenu selectByName(@Param("menuName") String menuName, @Param("path") String path);

	/**
	 * 父结点查所有子节点
	 */
	List<SysMenu> sonMenuList(@Param("menuId") Long menuId);
}
