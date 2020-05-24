package cn.icepear.dandelion.upm.api.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author rim-wood
 * @description 菜单表
 * @date Created on 2019-04-18.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysMenu extends Model<SysMenu> {

	private static final long serialVersionUID = 1L;

	/**
	 * 菜单ID
	 */
	@NotNull(message = "菜单ID不能为空")
	@TableId(value = "menu_id", type = IdType.ID_WORKER)
	private Long menuId;
	/**
	 * 菜单名称
	 */
	@NotBlank(message = "菜单名称不能为空")
	private String menuName;

	/**
	 * 父菜单ID
	 */
	@NotNull(message = "菜单父ID不能为空")
	private Long parentId;

	/**
	 * 菜单类型   0：目录   1：菜单   2：按钮
	 */
	@NotNull(message = "菜单类型不能为空")
	private Integer menuType;

	/**
	 * 前端URL
	 */
	private String path;

	/**
	 * VUE页面
	 */
	private String component;

	/**
	 * 图标
	 */
	private String icon;

	/**
	 * 菜单权限标识
	 */
	private String permission;

	/**
	 * 排序值
	 */
	private Integer sortOrder;

	/**
	 * 路由缓冲
	 */
	private int keepAlive;
	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createTime;

	/**
	 * 创建人
	 */
	private String creator;

	/**
	 * 修改时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime updateTime;

	/**
	 * 修改人
	 */
	private String updator;
	/**
	 * 0--正常 1--删除
	 */
	@TableLogic
	private int delFlag;


}
