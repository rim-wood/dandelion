package cn.icepear.dandelion.common.security.service;

import cn.icepear.dandelion.common.security.constant.SecurityConstants;
import lombok.SneakyThrows;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

import javax.sql.DataSource;

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

	/**
	 * 重写原生方法支持redis缓存
	 * todo 该方法在第一次执行存在null异常，需要处理
	 * @param clientId
	 * @return
	 */
	@Override
	@SneakyThrows
	@Cacheable(value = SecurityConstants.CLIENT_DETAILS_KEY, key = "#clientId", unless = "#result == null")
	public ClientDetails loadClientByClientId(String clientId) {
		return super.loadClientByClientId(clientId);
	}
}
