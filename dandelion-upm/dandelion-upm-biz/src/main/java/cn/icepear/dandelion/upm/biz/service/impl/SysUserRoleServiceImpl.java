package cn.icepear.dandelion.upm.biz.service.impl;

import cn.icepear.dandelion.upm.api.domain.entity.SysUserRole;
import cn.icepear.dandelion.upm.biz.mapper.SysUserRoleMapper;
import cn.icepear.dandelion.upm.biz.service.SysUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author rim-wood
 * @description 用户角色管理service实现
 * @date Created on 2019-04-18.
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

	/**
	 * 根据用户Id删除该用户的角色关系
	 *
	 * @param userId 用户ID
	 * @return boolean
	 */
	@Override
	public Boolean removeRoleByUserId(Integer userId) {
		return baseMapper.deleteByUserId(userId);
	}
}
