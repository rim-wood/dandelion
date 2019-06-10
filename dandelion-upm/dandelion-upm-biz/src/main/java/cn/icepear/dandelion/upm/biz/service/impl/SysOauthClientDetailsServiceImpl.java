package cn.icepear.dandelion.upm.biz.service.impl;

import cn.icepear.dandelion.common.security.constant.SecurityConstants;
import cn.icepear.dandelion.upm.api.domain.entity.SysOauthClientDetails;
import cn.icepear.dandelion.upm.biz.mapper.SysOauthClientDetailsMapper;
import cn.icepear.dandelion.upm.biz.service.SysOauthClientDetailsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

/**
 * @author rim-wood
 * @description Oauth client管理service实现
 * @date Created on 2019-04-18.
 */
@Service
public class SysOauthClientDetailsServiceImpl extends ServiceImpl<SysOauthClientDetailsMapper, SysOauthClientDetails> implements SysOauthClientDetailsService {

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
	 * 根据客户端信息
	 *
	 * @param clientDetails
	 * @return
	 */
	@Override
	@CacheEvict(value = SecurityConstants.CLIENT_DETAILS_KEY, key = "#clientDetails.clientId")
	public Boolean updateClientDetailsById(SysOauthClientDetails clientDetails) {
		return this.updateById(clientDetails);
	}
}
