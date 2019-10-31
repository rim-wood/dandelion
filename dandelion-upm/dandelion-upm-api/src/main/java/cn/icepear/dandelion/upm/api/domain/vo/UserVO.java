package cn.icepear.dandelion.upm.api.domain.vo;

import cn.icepear.dandelion.upm.api.domain.entity.SysRole;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author rim-wood
 * @description 用户显示对象
 * @date Created on 2019-04-18.
 */
@Data
public class UserVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	private Integer userId;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 真实姓名
	 */
	private String realName;

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

	/**
	 * 部门ID
	 */
	private Integer deptId;

	/**
	 * 部门名称
	 */
	private String deptName;
	/**
	 * 最顶级机构ID
	 */
	private Integer grandparentDeptId;

	/**
	 * 最顶级机构名称
	 */
	private String grandparentDeptName;

	/**
	 * 角色列表
	 */
	private List<SysRole> roleList;
}
