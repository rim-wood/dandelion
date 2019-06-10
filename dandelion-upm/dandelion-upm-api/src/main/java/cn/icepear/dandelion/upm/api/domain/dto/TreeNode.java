package cn.icepear.dandelion.upm.api.domain.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rim-wood
 * @description 树节点
 * @date Created on 2019-04-18.
 */
@Data
public class TreeNode {
	protected int id;
	protected int parentId;
	protected List<TreeNode> children = new ArrayList<TreeNode>();

	public void add(TreeNode node) {
		children.add(node);
	}
}
