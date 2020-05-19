package cn.icepear.dandelion.common.security.service;

import cn.icepear.dandelion.upm.api.domain.dto.RoleInfo;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author rimwood
 * @date 2019-04-15
 * 医院联网扩展用户
 */
@Setter
@Getter
public class DandelionUser extends User implements Serializable{
	/**
	 * 用户ID
	 */
	private Integer id;
	/**
	 * 部门ID
	 */
	private Integer deptId;
	/**
	 * 部门ID
	 */
	private Integer grandparentDeptId;
	/**
	 * 真实姓名
	 */
	private String realName;
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
	 * 角色集合
	 */
	private List<RoleInfo> roles;

	/**
	 * Construct the <code>User</code> with the details required by
	 * {@link DaoAuthenticationProvider}.
	 *
	 * @param id                    用户ID
	 * @param deptId                部门ID
	 * @param realName              真实姓名
	 * @param email                	邮箱
	 * @param mobile                电话
	 * @param avatar                头像
	 * @param roles                 用户对应角色信息
	 * @param username              the username presented to the
	 *                              <code>DaoAuthenticationProvider</code>
	 * @param password              the password that should be presented to the
	 *                              <code>DaoAuthenticationProvider</code>
	 * @param enabled               set to <code>true</code> if the user is enabled
	 * @param accountNonExpired     set to <code>true</code> if the account has not expired
	 * @param credentialsNonExpired set to <code>true</code> if the credentials have not
	 *                              expired
	 * @param accountNonLocked      set to <code>true</code> if the account is not locked
	 * @param authorities           the authorities that should be granted to the caller if they
	 *                              presented the correct username and password and the user is enabled. Not null.
	 * @throws IllegalArgumentException if a <code>null</code> value was passed either as
	 *                                  a parameter or as an element in the <code>GrantedAuthority</code> collection
	 */
	public DandelionUser(Integer id, Integer deptId, Integer grandparentDeptId, String realName, String email, String mobile, String avatar, List<RoleInfo> roles,
					   String username, String password, boolean enabled, boolean accountNonExpired,
					   boolean credentialsNonExpired, boolean accountNonLocked,
					   Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.id = id;
		this.deptId = deptId;
		this.grandparentDeptId = grandparentDeptId;
		this.realName = realName;
		this.email = email;
		this.mobile = mobile;
		this.avatar = avatar;
		this.roles = roles;
	}
}
