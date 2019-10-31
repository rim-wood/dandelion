package cn.icepear.dandelion.upm.biz.mapper;

import cn.icepear.dandelion.upm.api.domain.entity.SysUser;
import cn.icepear.dandelion.upm.api.domain.vo.UserVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author rim-wood
 * @description 用户管理 Mapper 接口
 * @date Created on 2019-04-18.
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
	/**
	 * 通过用户名查询用户信息（含有角色信息）
	 *
	 * @param userName 用户名
	 * @param userId 用户编号
	 * @return userVo
	 */
	UserVO getUserVoByUsernameOrId(@Param("userName") String userName,
								   @Param("userId") Integer userId);

	/**
	 * 分页查询用户信息（含角色）
	 *
	 * @param page    分页
	 * @param userName 用户名
	 * @param deptIds  要查看的机构编号
	 * @return list
	 */
	IPage<List<UserVO>> getUserVosPage(Page page, @Param("userName") String userName,@Param("deptIds")List<Integer> deptIds);

}
