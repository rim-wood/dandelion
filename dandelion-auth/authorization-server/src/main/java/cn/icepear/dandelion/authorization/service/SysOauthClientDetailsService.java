package cn.icepear.dandelion.authorization.service;

import cn.icepear.dandelion.authorization.entity.OauthClientDetails;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author rim-wood
 * @description Oauth client管理service接口
 * @date Created on 2019-04-18.
 */
public interface SysOauthClientDetailsService extends IService<OauthClientDetails> {
	/**
	 * 通过ID删除客户端
	 *
	 * @param id
	 * @return
	 */
	Boolean removeClientDetailsById(String id);

	/**
	 * 修改客户端信息
	 *
	 * @param oauthClientDetails
	 * @return
	 */
	Boolean updateClientDetailsById(OauthClientDetails oauthClientDetails);

	/**
	 * 查询客户端列表
	 */
	List<OauthClientDetails> getAllList();

	/**
	 * 添加客户端
	 */
	Boolean saveOauthClient(OauthClientDetails oauthClientDetails);


}
