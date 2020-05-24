package cn.icepear.dandelion.upm.biz.mapper;


import cn.icepear.dandelion.upm.api.domain.entity.SysRoleFunction;
import cn.icepear.dandelion.upm.api.domain.entity.SysRoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author rim-wood
 * @description 角色菜单管理 Mapper 接口
 * @date Created on 2019-04-18.
 */
@Mapper
public interface SysRoleFunctionMapper extends BaseMapper<SysRoleFunction> {

    /**
     * 添加角色与菜单关联
     */
    int saveSysRoleFunction(List<SysRoleFunction> sysRoleFunctions);
    
    /**
     * 按菜单id删除关联
     */
    void deleteByFunctionId(@Param("functionId") Long functionId);

    /**
     * 使用角色id删除角色-功能关联表数据
     */
    int deleteFunctionByRoleId(@Param("roleId") Long roleId);
}
