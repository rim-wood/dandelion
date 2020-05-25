package cn.icepear.dandelion.upm.biz.controller;

import cn.icepear.dandelion.common.core.constant.CommonConstants;
import cn.icepear.dandelion.common.core.utils.R;
import cn.icepear.dandelion.common.log.annotation.SysLog;
import cn.icepear.dandelion.common.security.annotation.JudgeUserRole;
import cn.icepear.dandelion.common.security.service.DandelionUser;
import cn.icepear.dandelion.common.security.utils.SecurityUtils;
import cn.icepear.dandelion.upm.api.domain.entity.SysRole;
import cn.icepear.dandelion.upm.api.domain.vo.RoleVO;
import cn.icepear.dandelion.upm.biz.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author rim-wood
 * @description 角色管理相关API
 * @date Created on 2019-08-01.
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 获取角色列表
     * @return
     */
    @GetMapping("/list")
    public R<List<SysRole>> getRoleList(@JudgeUserRole(role = "ADMIN") boolean isAdmin){
        DandelionUser user = SecurityUtils.getUser();
        if(!isAdmin){
            return new R<>(CommonConstants.SUCCESS,"查询成功",sysRoleService.getAllList(user.getGrandparentDeptId()));
        }
        return new R<>(CommonConstants.SUCCESS,"查询成功",sysRoleService.getAllList(null));
    }


    /**
     * 根据roleId获取角色信息
     */
    @GetMapping("/info/{roleId}")
    public R<RoleVO> getSysRoleById(@PathVariable Long roleId){
        RoleVO roleVO = sysRoleService.getSysRoleById(roleId);
        if (roleVO != null){
            return new R<>(CommonConstants.SUCCESS,"查询成功",roleVO);
        }
        return new R<>(CommonConstants.FAIL,"无此角色,或已被删除",null);
    }

    /**
     * 添加角色
     */
    @PostMapping("/add")
    @SysLog("添加角色信息")
    @PreAuthorize("@mse.hasPermission('sys:role:add')")
    public R<RoleVO> addSysDept(@RequestBody RoleVO roleVO, @JudgeUserRole(role = "ADMIN") boolean isAdmin){
        return sysRoleService.addRoleVO(roleVO,isAdmin);
    }

    /**
     * 修改角色
     */
    @PostMapping("/update")
    @SysLog("修改角色信息")
    @PreAuthorize("@mse.hasPermission('sys:role:update')")
    public R<RoleVO> updateSysDept(@RequestBody RoleVO roleVO, @JudgeUserRole(role = "ADMIN") boolean isAdmin){
        return sysRoleService.updateRoleVO(roleVO,isAdmin);
    }
}
