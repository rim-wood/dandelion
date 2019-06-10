package cn.icepear.dandelion.upm.api.domain.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author rim-wood
 * @description 部门树
 * @date Created on 2019-04-18.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DeptTree extends TreeNode {
	private String name;
}
