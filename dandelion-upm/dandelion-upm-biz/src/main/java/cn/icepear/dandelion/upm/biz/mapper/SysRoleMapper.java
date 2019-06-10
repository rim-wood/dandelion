package cn.icepear.dandelion.upm.biz.mapper;

import cn.icepear.dandelion.upm.api.domain.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author rim-wood
 * @description 角色管理 Mapper 接口
 * @date Created on 2019-04-18.
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {
	/**
	 * 通过用户ID，查询角色信息
	 *
	 * @param userId
	 * @return
	 */
	List<SysRole> listRolesByUserId(Integer userId);
}
