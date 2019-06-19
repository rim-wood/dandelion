package cn.icepear.dandelion.upm.biz.service.impl;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.icepear.dandelion.common.core.constant.CommonConstants;
import cn.icepear.dandelion.common.core.utils.StringUtils;
import cn.icepear.dandelion.common.security.utils.SecurityUtils;
import cn.icepear.dandelion.upm.api.domain.dto.RoleInfo;
import cn.icepear.dandelion.upm.api.domain.dto.UserDTO;
import cn.icepear.dandelion.upm.api.domain.dto.UserInfo;
import cn.icepear.dandelion.upm.api.domain.entity.SysDept;
import cn.icepear.dandelion.upm.api.domain.entity.SysRole;
import cn.icepear.dandelion.upm.api.domain.entity.SysUser;
import cn.icepear.dandelion.upm.api.domain.entity.SysUserRole;
import cn.icepear.dandelion.upm.api.domain.vo.MenuVO;
import cn.icepear.dandelion.upm.api.domain.vo.UserVO;
import cn.icepear.dandelion.upm.biz.mapper.SysUserMapper;
import cn.icepear.dandelion.upm.biz.service.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author rim-wood
 * @description 用户管理service实现
 * @date Created on 2019-04-18.
 */
@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
	private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();
	@Autowired
	private SysMenuService sysMenuService;
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysDeptService sysDeptService;
	@Autowired
	private SysUserRoleService sysUserRoleService;

	/**
	 * 保存用户信息
	 *
	 * @param userDto DTO 对象
	 * @return success/fail
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean saveUser(UserDTO userDto) {
		SysUser sysUser = new SysUser();
		BeanUtils.copyProperties(userDto, sysUser);
		sysUser.setDelFlag(CommonConstants.STATUS_NORMAL);
		sysUser.setPassword(ENCODER.encode(userDto.getPassword()));
		baseMapper.insert(sysUser);
		List<SysUserRole> userRoleList = userDto.getRole()
			.stream().map(roleId -> {
				SysUserRole userRole = new SysUserRole();
				userRole.setUserId(sysUser.getId());
				userRole.setRoleId(roleId);
				return userRole;
			}).collect(Collectors.toList());
		return sysUserRoleService.saveBatch(userRoleList);
	}

	/**
	 * 通过查用户的全部信息
	 *
	 * @param sysUser 用户
	 * @return
	 */
	@Override
	public UserInfo getUserInfo(SysUser sysUser) {
		UserInfo userInfo = new UserInfo();
		userInfo.setSysUser(sysUser);
		//设置角色列表  （ID）
		List<RoleInfo> roles = sysRoleService.listRolesByUserId(sysUser.getId())
			.stream()
			.map(sysRole->{
				RoleInfo roleInfo = new RoleInfo();
				BeanUtils.copyProperties(sysRole,roleInfo);
				return roleInfo;
			}).collect(Collectors.toList());
		userInfo.setRoles(roles);

		//设置权限列表（menu.permission）
		Set<String> permissions = new HashSet<>();
		roles.forEach(roleInfo -> {
			List<String> permissionList = sysMenuService.getMenuByRoleId(roleInfo.getRoleId())
				.stream()
				.filter(menuVo -> StringUtils.isNotEmpty(menuVo.getPermission()))
				.map(MenuVO::getPermission)
				.collect(Collectors.toList());
			permissions.addAll(permissionList);
		});
		userInfo.setPermissions(ArrayUtil.toArray(permissions, String.class));
		return userInfo;
	}

	/**
	 * 分页查询用户信息（含有角色信息）
	 *
	 * @param page    分页对象
	 * @param userDTO 参数列表
	 * @return
	 */
	@Override
	public IPage getUserWithRolePage(Page page, UserDTO userDTO) {
		return baseMapper.getUserVosPage(page, userDTO);
	}

	/**
	 * 通过ID查询用户信息
	 *
	 * @param id 用户ID
	 * @return 用户信息
	 */
	@Override
	public UserVO getUserVoById(Integer id) {
		return baseMapper.getUserVoById(id);
	}

	/**
	 * 删除用户
	 *
	 * @param sysUser 用户
	 * @return Boolean
	 */
	@Override
	@CacheEvict(value = "user_details", key = "#sysUser.username")
	public Boolean removeUserById(SysUser sysUser) {
		sysUserRoleService.removeRoleByUserId(sysUser.getId());
		this.removeById(sysUser.getId());
		return Boolean.TRUE;
	}

	@Override
	@CacheEvict(value = "user_details", key = "#userDto.username")
	public boolean updateUserInfo(UserDTO userDto) {
		UserVO userVO = baseMapper.getUserVoByUsername(userDto.getUserName());
		SysUser sysUser = new SysUser();
		if (StrUtil.isNotBlank(userDto.getPassword())
			&& StrUtil.isNotBlank(userDto.getNewpassword())) {
			if (ENCODER.matches(userDto.getPassword(), userVO.getPassword())) {
				sysUser.setPassword(ENCODER.encode(userDto.getNewpassword()));
			} else {
				log.warn("原密码错误，修改密码失败:{}", userDto.getUserName());
				return false;
			}
		}
		sysUser.setMobile(userDto.getMobile());
		sysUser.setId(userVO.getUserId());
		sysUser.setAvatar(userDto.getAvatar());
		return this.updateById(sysUser);
	}

	@Override
	@CacheEvict(value = "user_details", key = "#userDto.username")
	public Boolean updateUser(UserDTO userDto) {
		SysUser sysUser = new SysUser();
		BeanUtils.copyProperties(userDto, sysUser);
		sysUser.setUpdateTime(LocalDateTime.now());

		if (StrUtil.isNotBlank(userDto.getPassword())) {
			sysUser.setPassword(ENCODER.encode(userDto.getPassword()));
		}
		this.updateById(sysUser);

		sysUserRoleService.remove(Wrappers.<SysUserRole>update().lambda()
			.eq(SysUserRole::getUserId, userDto.getId()));
		userDto.getRole().forEach(roleId -> {
			SysUserRole userRole = new SysUserRole();
			userRole.setUserId(sysUser.getId());
			userRole.setRoleId(roleId);
			userRole.insert();
		});
		return Boolean.TRUE;
	}

	/**
	 * 查询上级部门的用户信息
	 *
	 * @param username 用户名
	 * @return R
	 */
	@Override
	public List<SysUser> listAncestorUsersByUsername(String username) {
		SysUser sysUser = this.getOne(Wrappers.<SysUser>query().lambda()
			.eq(SysUser::getUserName, username));

		SysDept sysDept = sysDeptService.getById(sysUser.getDeptId());
		if (sysDept == null) {
			return null;
		}

		Integer parentId = sysDept.getParentId();
		return this.list(Wrappers.<SysUser>query().lambda()
			.eq(SysUser::getDeptId, parentId));
	}

	/**
	 * 获取当前用户的子部门信息
	 *
	 * @return 子部门列表
	 */
	private List<Integer> getChildDepts() {
		Integer deptId = SecurityUtils.getUser().getDeptId();
		//获取当前部门的子部门
		return null;
	}

}
