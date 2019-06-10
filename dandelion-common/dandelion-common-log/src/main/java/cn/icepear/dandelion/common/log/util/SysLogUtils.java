package cn.icepear.dandelion.common.log.util;

import cn.hutool.core.util.URLUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.HttpUtil;
import cn.icepear.dandelion.common.core.constant.CommonConstants;
import cn.icepear.dandelion.common.log.domain.SysLogEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 系统日志工具类
 *
 * @author L.cm
 */
public class SysLogUtils {
	public static SysLogEntity getSysLog() {
		HttpServletRequest request = ((ServletRequestAttributes) Objects
			.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
		SysLogEntity sysLogEntity = new SysLogEntity();
		sysLogEntity.setCreateBy(Objects.requireNonNull(getUsername()));
		sysLogEntity.setType(CommonConstants.STATUS_NORMAL);
		sysLogEntity.setRemoteAddr(ServletUtil.getClientIP(request));
		sysLogEntity.setRequestUri(URLUtil.getPath(request.getRequestURI()));
		sysLogEntity.setMethod(request.getMethod());
		sysLogEntity.setUserAgent(request.getHeader("user-agent"));
		sysLogEntity.setParams(HttpUtil.toParams(request.getParameterMap()));
		sysLogEntity.setServiceId(getClientId());
		return sysLogEntity;
	}

	/**
	 * 获取客户端
	 *
	 * @return clientId
	 */
	private static String getClientId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof OAuth2Authentication) {
			OAuth2Authentication auth2Authentication = (OAuth2Authentication) authentication;
			return auth2Authentication.getOAuth2Request().getClientId();
		}
		return null;
	}

	/**
	 * 获取用户名称
	 *
	 * @return username
	 */
	private static String getUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return null;
		}
		return authentication.getName();
	}

}
