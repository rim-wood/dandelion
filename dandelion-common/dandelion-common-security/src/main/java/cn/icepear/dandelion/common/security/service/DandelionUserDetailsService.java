package cn.icepear.dandelion.common.security.service;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.icepear.dandelion.common.core.constant.CommonConstants;
import cn.icepear.dandelion.common.core.utils.R;
import cn.icepear.dandelion.common.core.utils.RedisUtils;
import cn.icepear.dandelion.common.security.constant.SecurityConstants;
import cn.icepear.dandelion.upm.api.domain.dto.UserInfo;
import cn.icepear.dandelion.upm.api.domain.entity.SysUser;
import cn.icepear.dandelion.upm.api.remote.RemoteUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author rimwood
 * @date 2019-04-15
 * 用户详细信息
 */
@Slf4j
@Service("userDetailsService")
public class DandelionUserDetailsService implements UserDetailsService {
	@Reference(version = "1.0.0", url = "dubbo://127.0.0.1:18008")
	private RemoteUserService remoteUserService;
	@Autowired
	private RedisUtils redisUtils;

	/**
	 * 用户密码登录
	 *
	 * @param username 用户名
	 * @return
	 * @throws UsernameNotFoundException
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		DandelionUser dandelionUser = redisUtils.get(username,DandelionUser.class);
		if (dandelionUser != null) {
			return dandelionUser;
		}

		R<UserInfo> result = remoteUserService.info(username, SecurityConstants.FROM_IN);
		if(result.getCode()==0){
			DandelionUser userDetails = getUserDetails(result);
			redisUtils.set(username, userDetails);
			return userDetails;
		}
		throw new UsernameNotFoundException(username);
	}

	/**
	 * 构建userdetails
	 *
	 * @param result 用户信息
	 * @return
	 */
	private DandelionUser getUserDetails(R<UserInfo> result) {
		if (result == null || result.getData() == null) {
			throw new UsernameNotFoundException("用户不存在");
		}

		UserInfo info = result.getData();
		Set<String> dbAuthsSet = new HashSet<>();
		if (ArrayUtil.isNotEmpty(info.getPermissions())) {
			// 获取权限
			dbAuthsSet.addAll(Arrays.asList(info.getPermissions()));

		}
		Collection<? extends GrantedAuthority> authorities
			= AuthorityUtils.createAuthorityList(dbAuthsSet.toArray(new String[0]));
		SysUser user = info.getSysUser();

		// 构造security用户
		return new DandelionUser(user.getId(), user.getDeptId(),info.getRoles(), user.getUserName(), SecurityConstants.BCRYPT + user.getPassword(),
			StrUtil.equals(user.getLockFlag(), CommonConstants.STATUS_NORMAL), true, true, true, authorities);
	}
}
