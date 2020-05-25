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
	List<SysRole> listRolesByUserId(Long userId);



	/**
	 * 添加角色
	 */
	int saveSysRole(RoleVO roleVO);


	/**
	 * 更新角色信息
	 */
	int updateRole(RoleVO roleVO);

	/**
	 * 通过角色Id，查询角色信息
	 */
	RoleVO getSysRoleById(Long roleId);

	/**
	 * 只查所在科室
	 */
	List<SysRole> getSysRoleByDeptId(@Param("deptId") Long deptId);

	/**
	 * 查询角色
	 */
	RoleVO getRoleVo(RoleVO roleVO);
}
