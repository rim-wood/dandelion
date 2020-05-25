package cn.icepear.dandelion.upm.api.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author rim-wood
 * @description 树节点
 * @date Created on 2019-04-18.
 */
@Data
public class TreeNode<T> implements Serializable {
	protected Long id;
	protected Long parentId;
	protected List<T> children = new ArrayList<T>();

	public void add(T node) {
		children.add(node);
	}
}
