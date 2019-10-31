package cn.icepear.dandelion.upm.biz.mapper;

import cn.icepear.dandelion.upm.api.domain.entity.SysRole;
import cn.icepear.dandelion.upm.api.domain.entity.SysRoleMenu;
import cn.icepear.dandelion.upm.api.domain.vo.RoleVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author rim-wood
 * @description 角色管理 Mapper 接口
 * @date Created on 2019-04-18.
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {
	/**
	 * 通过用户ID，查询角色信息
	 *
	 * @param userId
	 * @return
	 */
	List<SysRole> listRolesByUserId(Integer userId);



	/**
	 * 添加角色
	 */
	int saveSysRole(RoleVO roleVO);

	/**
	 * 添加角色与菜单关联
	 */
	int saveSysRoleMenu(List<SysRoleMenu> sysRoleMenus);

	/**
	 * 使用角色id删除角色-菜单关联表数据
	 */
	int deleteRoleIdMenu(Integer roleId);

	/**
	 * 更新角色信息
	 */
	int updateRole(RoleVO roleVO);

	/**
	 * 通过角色Id，查询角色信息
	 */
	RoleVO getSysRoleById(Integer roleId);

	/**
	 * 只查所在科室
	 */
	List<SysRole> getSysRoleByDeptId(@Param("deptId") Integer deptId);

	/**
	 * 查询角色
	 */
	RoleVO getRoleVo(RoleVO roleVO);
}
