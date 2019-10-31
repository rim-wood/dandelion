package cn.icepear.dandelion.upm.api.domain.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author rim-wood
 * @description 部门树
 * @date Created on 2019-04-18.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class DeptTree extends TreeNode<DeptTree> {
	private String deptName;
	/**
	 * 部门类型
	 */
	private String deptType;

	/**
	 * 排序
	 */
	private Integer sortOrder;

	/**
	 * 是否删除  1：已删除  0：正常
	 */
	private int delFlag;


	public DeptTree(String deptName, String deptType, Integer sortOrder, int delFlag) {
		super();
		this.deptName = deptName;
		this.deptType = deptType;
		this.sortOrder = sortOrder;
		this.delFlag = delFlag;
	}
}
