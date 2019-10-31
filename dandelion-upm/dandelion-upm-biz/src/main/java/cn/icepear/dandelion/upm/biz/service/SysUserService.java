package cn.icepear.dandelion.upm.biz.service;

import cn.icepear.dandelion.upm.api.domain.dto.UserDTO;
import cn.icepear.dandelion.upm.api.domain.dto.UserInfo;
import cn.icepear.dandelion.upm.api.domain.entity.SysUser;
import cn.icepear.dandelion.upm.api.domain.vo.UserVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author rim-wood
 * @description 用户管理service接口
 * @date Created on 2019-04-18.
 */
public interface SysUserService extends IService<SysUser> {
	/**
	 * 查询用户信息
	 *
	 * @param sysUser 用户
	 * @return userInfo
	 */
	UserInfo getUserInfo(SysUser sysUser);

	/**
	 * 按用户名查询用户信息
	 * @param userName
	 * @return
	 */
	UserVO getUserVoByUserName(String userName);

	/**
	 * 分页查询用户信息（含有角色信息）
	 *
	 * @param page    分页对象
	 * @param userName 用户名
	 * @param deptId  要查看的机构编号
	 * @return
	 */
	IPage<List<UserVO>> getUserWithRolePage(Page page, String userName,Integer deptId);

	/**
	 * 删除用户
	 *
	 * @param sysUser 用户
	 * @return boolean
	 */
	Boolean removeUserById(SysUser sysUser);

	/**
	 * 更新当前用户基本信息
	 *
	 * @param userDto 用户信息
	 * @return Boolean
	 */
	boolean updateUserInfo(UserDTO userDto);

	/**
	 * 更新指定用户信息
	 *
	 * @param userDto 用户信息
	 * @return
	 */
	Boolean updateUser(UserDTO userDto);

	/**
	 * 通过ID查询用户信息
	 *
	 * @param id 用户ID
	 * @return 用户信息
	 */
	UserVO getUserVoById(Integer id);

	/**
	 * 查询上级部门的用户信息
	 *
	 * @param username 用户名
	 * @return R
	 */
	List<SysUser> listAncestorUsersByUsername(String username);

	/**
	 * 保存用户信息
	 *
	 * @param userDto DTO 对象
	 * @return success/fail
	 */
	Boolean saveUser(UserDTO userDto);

	/**
	 * 用户注册
	 *
	 * @param userDto DTO 对象
	 * @return SysUser
	 */
	SysUser registUser(UserDTO userDto);
}
