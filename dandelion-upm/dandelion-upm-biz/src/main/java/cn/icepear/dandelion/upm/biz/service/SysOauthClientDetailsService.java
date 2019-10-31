package cn.icepear.dandelion.upm.biz.service;

import cn.icepear.dandelion.upm.api.domain.entity.SysOauthClientDetails;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author rim-wood
 * @description Oauth client管理service接口
 * @date Created on 2019-04-18.
 */
public interface SysOauthClientDetailsService extends IService<SysOauthClientDetails> {
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
	 * @param sysOauthClientDetails
	 * @return
	 */
	Boolean updateClientDetailsById(SysOauthClientDetails sysOauthClientDetails);

	/**
	 * 查询客户端列表
	 */
	List<SysOauthClientDetails> getAllList();

	/**
	 * 添加客户端
	 */
	Boolean saveOauthClient(SysOauthClientDetails sysOauthClientDetails);


}
