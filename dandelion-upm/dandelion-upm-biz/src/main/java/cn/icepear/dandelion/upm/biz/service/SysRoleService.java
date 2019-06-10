package cn.icepear.dandelion.upm.biz.service;

import cn.icepear.dandelion.upm.api.domain.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author rim-wood
 * @description 角色管理service接口
 * @date Created on 2019-04-18.
 */
public interface SysRoleService extends IService<SysRole> {

	/**
	 * 通过用户ID，查询角色信息
	 *
	 * @param userId
	 * @return
	 */
	List<SysRole> listRolesByUserId(Integer userId);

	/**
	 * 通过角色ID，删除角色
	 *
	 * @param id
	 * @return
	 */
	Boolean removeRoleById(Integer id);
}
