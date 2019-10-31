package cn.icepear.dandelion.upm.api.domain.dto;

import cn.icepear.dandelion.upm.api.domain.vo.MenuVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author rim-wood
 * @description 菜单树
 * @date Created on 2019-04-18.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MenuTree extends TreeNode<MenuTree> implements Serializable {
	private String icon;      //菜单头像
	private String menuName;      //菜单名称
	private boolean spread = false;
	private String path;      //菜单地址
	private String component; //前端组件
	private String permission; //权限
	private String redirect;
	private String keepAlive; //是否开启
	private String code;
	private Integer menuType;     //菜单类型   0：目录   1：菜单   2：按钮
	private String label;
	private Integer sortOrder;     //排序
	private int delFlag;      //删除标识0--正常 1--删除
	public MenuTree() {
	}

	public MenuTree(int id, String name, int parentId) {
		this.id = id;
		this.parentId = parentId;
		this.menuName = menuName;
		this.label = menuName;
	}

	public MenuTree(int id, String menuName, MenuTree parent) {
		this.id = id;
		this.parentId = parent.getId();
		this.menuName = menuName;
		this.label = menuName;
	}

	public MenuTree(MenuVO menuVo) {
		this.id = menuVo.getMenuId();
		this.parentId = menuVo.getParentId();
		this.icon = menuVo.getIcon();
		this.menuName = menuVo.getMenuName();
		this.path = menuVo.getPath();
		this.component = menuVo.getComponent();
		this.menuType = menuVo.getMenuType();
		this.label = menuVo.getMenuName();
		this.sortOrder = menuVo.getSortOrder();
		this.keepAlive = menuVo.getKeepAlive();
	}
}
