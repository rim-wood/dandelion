package cn.icepear.dandelion.upm.biz.service;

import cn.icepear.dandelion.upm.api.domain.dto.DeptTree;
import cn.icepear.dandelion.upm.api.domain.entity.SysDept;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author rim-wood
 * @description 部门管理service接口
 * @date Created on 2019-04-18.
 */
public interface SysDeptService extends IService<SysDept> {

	/**
	 * 查询部门树菜单
	 *
	 * @return DeptTree 树
	 */
	List<DeptTree> getDeptTree();

	/**
	 * 查询部门树菜单
	 *
	 * @return DeptTree 树
	 */
	List<SysDept> getSysDeptList();

	/**
	 * 查询用户部门树
	 *
	 * @return DeptTree 树
	 */
	List<DeptTree> getCurrentUserDeptTrees(Long deptId);

	/**
	 * 查询用户部门树
	 *
	 * @return DeptTree 树
	 */
	List<SysDept> getCurrentUserSysDeptList(Long deptId);

	/**
	 * 添加信息部门
	 *
	 * @param sysDept
	 * @return
	 */
	Boolean saveDept(SysDept sysDept);

	/**
	 * 删除部门
	 *
	 * @param id 部门 ID
	 * @return 成功、失败
	 */
	Boolean removeDeptById(Long id);

	/**
	 * 更新部门
	 *
	 * @param sysDept 部门信息
	 * @return 成功、失败
	 */
	Boolean updateDeptById(SysDept sysDept);

	/**
	 * 获取当前用户的父级机构
	 * @param deptId
	 * @return
	 */
	List<SysDept>  getParentDeptList(Long deptId);

	/**
	 * 根据deptId获取机构信息
	 * @param deptId
	 * @return
	 */
	SysDept getSysDeptById(Long deptId);

}
