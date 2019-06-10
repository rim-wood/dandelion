package cn.icepear.dandelion.upm.biz.service;


import cn.icepear.dandelion.upm.api.domain.entity.SysUserRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author rim-wood
 * @description 用户角色管理service接口
 * @date Created on 2019-04-18.
 */
public interface SysUserRoleService extends IService<SysUserRole> {

	/**
	 * 根据用户Id删除该用户的角色关系
	 *
	 * @param userId 用户ID
	 * @return boolean
	 * @author 寻欢·李
	 * @date 2017年12月7日 16:31:38
	 */
	Boolean removeRoleByUserId(Integer userId);
}
