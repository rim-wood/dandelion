package cn.icepear.dandelion.upm.api.domain.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author rim-wood
 * @description 菜单对外实体
 * @date Created on 2019-04-18.
 */
@Data
public class MenuVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 菜单ID
	 */
	private Integer menuId;
	/**
	 * 菜单名称
	 */
	private String menuName;

	/**
	 * 父菜单ID
	 */
	private Integer parentId;

	private Integer menuType;

	/**
	 * 图标
	 */
	private String icon;
	/**
	 * 一个路径
	 */
	private String path;
	/**
	 * VUE页面
	 */
	private String component;

	/**
	 * 菜单权限标识
	 */
	private String permission;
	/**
	 * 排序值
	 */
	private Integer sortOrder;
	/**
	 * 是否缓冲
	 */
	private String keepAlive;
	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;
	/**
	 * 更新时间
	 */
	private LocalDateTime updateTime;
	/**
	 * 0--正常 1--删除
	 */
	private String delFlag;


	@Override
	public int hashCode() {
		return menuId.hashCode();
	}

	/**
	 * menuId 相同则相同
	 *
	 * @param obj
	 * @return
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof MenuVO) {
			Integer targetMenuId = ((MenuVO) obj).getMenuId();
			return menuId.equals(targetMenuId);
		}
		return super.equals(obj);
	}
}