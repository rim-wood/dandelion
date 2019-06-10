package cn.icepear.dandelion.upm.api.domain.dto;

import cn.icepear.dandelion.upm.api.domain.vo.MenuVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author rim-wood
 * @description 菜单树
 * @date Created on 2019-04-18.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MenuTree extends TreeNode {
	private String icon;
	private String name;
	private boolean spread = false;
	private String path;
	private String component;
	private String authority;
	private String redirect;
	private String keepAlive;
	private String code;
	private Integer type;
	private String label;
	private Integer sort;

	public MenuTree() {
	}

	public MenuTree(int id, String name, int parentId) {
		this.id = id;
		this.parentId = parentId;
		this.name = name;
		this.label = name;
	}

	public MenuTree(int id, String name, MenuTree parent) {
		this.id = id;
		this.parentId = parent.getId();
		this.name = name;
		this.label = name;
	}

	public MenuTree(MenuVO menuVo) {
		this.id = menuVo.getMenuId();
		this.parentId = menuVo.getParentId();
		this.icon = menuVo.getIcon();
		this.name = menuVo.getMenuName();
		this.path = menuVo.getPath();
		this.component = menuVo.getComponent();
		this.type = menuVo.getMenuType();
		this.label = menuVo.getMenuName();
		this.sort = menuVo.getSortOrder();
		this.keepAlive = menuVo.getKeepAlive();
	}
}
