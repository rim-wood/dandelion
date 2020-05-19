package cn.icepear.dandelion.authorization.service.impl;
import cn.icepear.dandelion.authorization.entity.OauthClientDetails;
import cn.icepear.dandelion.authorization.mapper.SysOauthClientDetailsMapper;
import cn.icepear.dandelion.authorization.service.SysOauthClientDetailsService;
import cn.icepear.dandelion.common.security.constant.SecurityConstants;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author rim-wood
 * @description Oauth client管理service实现
 * @date Created on 2019-04-18.
 */
@Service
public class SysOauthClientDetailsServiceImpl extends ServiceImpl<SysOauthClientDetailsMapper, OauthClientDetails> implements SysOauthClientDetailsService {

	/**
	 * 通过ID删除客户端
	 *
	 * @param id
	 * @return
	 */
	@Override
	@CacheEvict(value = SecurityConstants.CLIENT_DETAILS_KEY, key = "#id")
	public Boolean removeClientDetailsById(String id) {
		return this.removeById(id);
	}

	/**
	 * 修改客户端信息
	 *
	 * @param clientDetails
	 * @return
	 */
	@Override
	@CacheEvict(value = SecurityConstants.CLIENT_DETAILS_KEY, key = "#clientDetails.clientId")
	public Boolean updateClientDetailsById(OauthClientDetails clientDetails) {
		return this.updateById(clientDetails);
	}

	@Override
	public List<OauthClientDetails> getAllList() {
		return this.list();
	}

	@Override
	public Boolean saveOauthClient(OauthClientDetails oauthClientDetails) {
		return this.save(oauthClientDetails);
	}

}
