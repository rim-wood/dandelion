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
	List<DeptTree> listDeptTrees();

	/**
	 * 查询用户部门树
	 *
	 * @return DeptTree 树
	 */
	List<DeptTree> listCurrentUserDeptTrees();

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
	Boolean removeDeptById(Integer id);

	/**
	 * 更新部门
	 *
	 * @param sysDept 部门信息
	 * @return 成功、失败
	 */
	Boolean updateDeptById(SysDept sysDept);

}
