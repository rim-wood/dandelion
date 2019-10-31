package cn.icepear.dandelion.upm.biz.mapper;


import cn.icepear.dandelion.upm.api.domain.entity.SysRoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author rim-wood
 * @description 角色菜单管理 Mapper 接口
 * @date Created on 2019-04-18.
 */
@Mapper
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

    /**
     * 按菜单id删除关联
     */
    void deleteByMenuId(@Param("menuId") Integer menuId);
}
