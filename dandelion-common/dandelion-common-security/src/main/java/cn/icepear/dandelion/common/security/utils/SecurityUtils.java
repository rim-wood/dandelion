package cn.icepear.dandelion.common.security.utils;


import cn.hutool.core.util.StrUtil;
import cn.icepear.dandelion.common.security.service.DandelionUser;
import cn.icepear.dandelion.common.security.constant.SecurityConstants;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author rimwood
 * @date 2019-04-15
 * 安全工具类
 */
//自动将所有域对象修改为static；而且自动创建一个私有的构造器
@UtilityClass
public class SecurityUtils {

	/**
	 * 获取Authentication
	 */
	public static Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	/**
	 * 获取用户
	 */
	public DandelionUser getUser(Authentication authentication) {
		Object principal = authentication.getPrincipal();
		if (principal instanceof DandelionUser) {
			return (DandelionUser) principal;
		}
		return null;
	}

	/**
	 * 获取用户
	 */
	public DandelionUser getUser() {
		Authentication authentication = getAuthentication();
		if (authentication == null) {
			return null;
		}
		return getUser(authentication);
	}

	/**
	 * 获取用户角色信息
	 *
	 * @return 角色集合
	 */
	public List<Integer> getRoles() {
		Authentication authentication = getAuthentication();
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

		List<Integer> roleIds = new ArrayList<>();
		authorities.stream()
			.filter(granted -> StrUtil.startWith(granted.getAuthority(), SecurityConstants.ROLE))
			.forEach(granted -> {
				String id = StrUtil.removePrefix(granted.getAuthority(), SecurityConstants.ROLE);
				roleIds.add(Integer.parseInt(id));
			});
		return roleIds;
	}
}
