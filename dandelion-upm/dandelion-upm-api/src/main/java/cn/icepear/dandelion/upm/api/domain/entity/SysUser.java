package cn.icepear.dandelion.upm.api.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author rim-wood
 * @description 角色菜单对应实体类
 * @date Created on 2019-04-18.
 */
@Data
@TableName("sys_user")
public class SysUser implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 用户名
	 */
	private String userName;

	private String password;
	/**
	 * 随机盐
	 */
	@JsonIgnore
	private String salt;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 电话
	 */
	private String mobile;
	/**
	 * 头像
	 */
	private String avatar;

	/**
	 * 部门ID
	 */
	private Integer deptId;

	/**
	 * 锁定标记
	 */
	private String lockFlag;

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
	 * 0-正常，1-删除
	 */
	@TableLogic
	private String delFlag;

}
