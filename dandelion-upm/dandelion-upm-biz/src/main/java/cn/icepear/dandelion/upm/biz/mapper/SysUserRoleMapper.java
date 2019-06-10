package cn.icepear.dandelion.upm.biz.mapper;


import cn.icepear.dandelion.upm.api.domain.entity.SysUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author rim-wood
 * @description 用户角色管理 Mapper 接口
 * @date Created on 2019-04-18.
 */
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {
	/**
	 * 根据用户Id删除该用户的角色关系
	 *
	 * @param userId 用户ID
	 * @return boolean
	 */
	Boolean deleteByUserId(@Param("userId") Integer userId);
}
