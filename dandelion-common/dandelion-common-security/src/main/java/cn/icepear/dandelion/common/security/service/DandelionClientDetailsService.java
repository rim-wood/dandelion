package cn.icepear.dandelion.common.security.service;

import cn.icepear.dandelion.common.core.utils.RedisUtils;
import cn.icepear.dandelion.common.security.component.error.DandelionOAuth2Exception;
import cn.icepear.dandelion.common.security.constant.ClientStatus;
import cn.icepear.dandelion.common.security.constant.SecurityConstants;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.common.exceptions.ClientAuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author rimwood
 * @date 2019/2/1
 * @description oauth客户端处理
 * 具体实现通过 JdbcClientDetailsService
 */
public class DandelionClientDetailsService extends JdbcClientDetailsService {

	public DandelionClientDetailsService(DataSource dataSource) {
		super(dataSource);
	}

	@Autowired
	private RedisUtils redisUtils;

	/**
	 * 重写原生方法
	 * 1.支持redis缓存
	 * 2.添加AdditionalInformation信息判断客户端状态
	 * @param clientId
	 * @return
	 */
	@Override
	@SneakyThrows
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		BaseClientDetails clientDetails = redisUtils.get(clientId,BaseClientDetails.class);
		if(clientDetails == null) {
			clientDetails = (BaseClientDetails) super.loadClientByClientId(clientId);
			if (clientDetails != null && clientDetails.getAdditionalInformation() != null) {
				if (clientDetails.getAdditionalInformation().containsKey("status") && !clientDetails.getAdditionalInformation().get("status").equals(ClientStatus.NORMAL.toString())) {
					throw new ClientRegistrationException("client status is " + String.valueOf(clientDetails.getAdditionalInformation().get("status")));
				}
			}
			redisUtils.set("client:details:"+clientId,clientDetails);
		}
		return clientDetails;
	}
}
