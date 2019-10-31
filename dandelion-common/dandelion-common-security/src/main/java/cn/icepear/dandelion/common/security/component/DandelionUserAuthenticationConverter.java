package cn.icepear.dandelion.common.security.component;

import cn.icepear.dandelion.common.core.utils.SpringContextHolder;
import cn.icepear.dandelion.common.security.service.DandelionUser;
import cn.icepear.dandelion.upm.api.domain.dto.RoleInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author rimwood
 * @description convertUserAuthentication构建checktoken的返回结果，extractAuthentication根据 checktoken 的结果转化用户信息
 * @date 2019-06-01
 */
public class DandelionUserAuthenticationConverter implements UserAuthenticationConverter {
	private static final String USER_ID = "user_id";
	private static final String DEPT_ID = "dept_id";
	private static final String GRANDPARENT_DEPT_ID = "grandparentDeptId";
	private static final String REAL_NAME = "realName";
	private static final String EMAIL = "email";
	private static final String MOBILE = "mobile";
	private static final String AVATAR = "avatar";
	private static final String USERNAME = "userName";
	private static final String TENANT_ID = "tenant_id";
	private static final String ROLES = "roles";
	private static final String N_A = "N/A";

	/**
	 * Extract information about the user to be used in an access token (i.e. for resource servers).
	 *
	 * @param authentication an authentication representing a user
	 * @return a map of key values representing the unique information about the user
	 */
	@Override
	public Map<String, ?> convertUserAuthentication(Authentication authentication) {
		Map<String, Object> response = new LinkedHashMap<>();
		DandelionUser dandelionUser = (DandelionUser) authentication.getPrincipal();
		response.put(USER_ID,dandelionUser.getId());
		response.put(DEPT_ID,dandelionUser.getDeptId());
		response.put(GRANDPARENT_DEPT_ID,dandelionUser.getGrandparentDeptId());
		response.put(REAL_NAME,dandelionUser.getRealName());
		response.put(EMAIL,dandelionUser.getEmail());
		response.put(MOBILE,dandelionUser.getMobile());
		response.put(AVATAR,dandelionUser.getAvatar());
		response.put(USERNAME, authentication.getName());
		response.put(ROLES,dandelionUser.getRoles());
		if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
			response.put(AUTHORITIES, AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
		}
		return response;
	}

	/**
	 * Inverse of {@link #convertUserAuthentication(Authentication)}. Extracts an Authentication from a map.
	 *
	 * @param map a map of user information
	 * @return an Authentication representing the user or null if there is none
	 */
	@Override
	public Authentication extractAuthentication(Map<String, ?> map) {
		if (map.containsKey(USERNAME)) {
			Collection<? extends GrantedAuthority> authorities = getAuthorities(map);

			String username = (String) map.get(USERNAME);
			Integer id = (Integer) map.get(USER_ID);
			Integer deptId = (Integer) map.get(DEPT_ID);
			Integer grandparentDeptId = (Integer) map.get(GRANDPARENT_DEPT_ID);
			String realName = (String) map.get(REAL_NAME);
			String email = (String) map.get(EMAIL);
			String mobile = (String) map.get(MOBILE);
			String avatar = (String) map.get(AVATAR);
			String scopesStr,rolesStr = "";
			List<RoleInfo> roles = null;
			/**
			 * ObjectMapper无法通过注入的方式注入，就算采用ConditionalOnBean的方式也没办法注入，原因未知
			 */
			ObjectMapper objectMapper = SpringContextHolder.getBean(ObjectMapper.class);
			try {
				rolesStr = objectMapper.writeValueAsString(map.get(ROLES));
				roles = objectMapper.readValue(rolesStr, new TypeReference<List<RoleInfo>>(){});
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}catch (IOException e) {
				e.printStackTrace();
			}
			DandelionUser user = new DandelionUser(id, deptId,grandparentDeptId,realName,email,mobile,avatar,roles, username, N_A, true
					, true, true, true, authorities);
			return new UsernamePasswordAuthenticationToken(user, N_A, authorities);
		}
		return null;
	}

	private Collection<? extends GrantedAuthority> getAuthorities(Map<String, ?> map) {
		if(map.containsKey(AUTHORITIES)) {
			Object authorities = map.get(AUTHORITIES);
			if (authorities instanceof String) {
				return AuthorityUtils.commaSeparatedStringToAuthorityList((String) authorities);
			}
			if (authorities instanceof Collection) {
				return AuthorityUtils.commaSeparatedStringToAuthorityList(StringUtils
						.collectionToCommaDelimitedString((Collection<?>) authorities));
			}
			throw new IllegalArgumentException("Authorities must be either a String or a Collection");
		}
		return null;
	}
}
