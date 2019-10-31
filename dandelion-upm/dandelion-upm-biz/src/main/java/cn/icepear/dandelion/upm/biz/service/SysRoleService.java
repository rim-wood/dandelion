package cn.icepear.dandelion.upm.biz.service;

import cn.icepear.dandelion.common.core.utils.R;
import cn.icepear.dandelion.upm.api.domain.entity.SysRole;
import cn.icepear.dandelion.upm.api.domain.vo.RoleVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author rim-wood
 * @description 角色管理service接口
 * @date Created on 2019-04-18.
 */
public interface SysRoleService extends IService<SysRole> {

	/**
	 * 通过用户ID，查询角色信息
	 *
	 * @param userId
	 * @return
	 */
	List<SysRole> listRolesByUserId(Integer userId);

	/**
	 * 通过角色ID，删除角色
	 *
	 * @param id
	 * @return
	 */
	Boolean removeRoleById(Integer id);

	/**
	 * 添加角色
	 */
	R<RoleVO> addRoleVO(RoleVO roleVo, Boolean isAdmin);

	/**
	 * 修改角色
	 */
	R<RoleVO> updateRoleVO(RoleVO roleVo,Boolean isAdmin);

	/**
	 * 通过角色Id，查询角色信息
	 */
	RoleVO getSysRoleById(Integer roleId);

	/**
	 * 根据机构id获取角色列表
	 */
	List<SysRole> getAllList(Integer deptId);
}
