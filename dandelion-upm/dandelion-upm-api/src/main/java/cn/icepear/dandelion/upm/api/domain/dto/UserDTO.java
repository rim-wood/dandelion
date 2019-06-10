package cn.icepear.dandelion.upm.api.domain.dto;

import cn.icepear.dandelion.upm.api.domain.entity.SysUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author rim-wood
 * @description 用户中间对象
 * @date Created on 2019-04-18.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserDTO extends SysUser {
	/**
	 * 角色ID
	 */
	private List<Integer> role;

	/**
	 * 新密码
	 */
	private String newpassword;
}
