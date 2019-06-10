package cn.icepear.dandelion.upm.biz.mapper;

import cn.icepear.dandelion.upm.api.domain.entity.SysMenu;
import cn.icepear.dandelion.upm.api.domain.vo.MenuVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

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
	List<MenuVO> listMenusByRoleId(Integer roleId);

	/**
	 * 通过角色ID查询权限
	 *
	 * @param roleIds Ids
	 * @return
	 */
	List<String> listPermissionsByRoleIds(String roleIds);
}
