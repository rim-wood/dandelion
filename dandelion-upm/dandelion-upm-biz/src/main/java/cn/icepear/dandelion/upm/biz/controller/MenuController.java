package cn.icepear.dandelion.upm.biz.controller;

import cn.icepear.dandelion.common.core.constant.CommonConstants;
import cn.icepear.dandelion.common.core.utils.R;
import cn.icepear.dandelion.common.log.annotation.SysLog;
import cn.icepear.dandelion.common.security.annotation.JudgeUserRole;
import cn.icepear.dandelion.common.security.service.DandelionUser;
import cn.icepear.dandelion.common.security.utils.SecurityUtils;
import cn.icepear.dandelion.upm.api.domain.entity.SysMenu;
import cn.icepear.dandelion.upm.api.domain.vo.SystemToMenuVo;
import cn.icepear.dandelion.upm.api.utils.List2TreeNodeUtil;
import cn.icepear.dandelion.upm.biz.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;


/**
 * @author rim-wood
 * @description 菜单相关API
 * @date Created on 2019-08-01.
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 获取菜单树结构
     * @return
     */
    @GetMapping("/tree")
    @SysLog("获取菜单树信息")
    public R<List<SystemToMenuVo>> getMenuTree(@RequestParam("systemId")String systemId, @JudgeUserRole(role = "ADMIN") boolean isAdmin){
        DandelionUser user = SecurityUtils.getUser();
        //调用获取菜单列表接口，获取菜单，再通过List2TreeNodeUtil.rebuildList2Tree(systemToMenuVo)将菜单列表转菜单树
        List<SystemToMenuVo> systemToMenuVo = sysMenuService.getMenuTreeList(user.getUsername(),user.getRoles(),systemId,isAdmin);
        return new R<>(CommonConstants.SUCCESS, "查询成功", (List<SystemToMenuVo>) List2TreeNodeUtil.rebuildList2Tree(systemToMenuVo));
    }

    /**
     * 获取菜单树列表
     * @return
     */
    @GetMapping("/treeList")
    @SysLog("获取菜单树信息")
    public R<List<SystemToMenuVo>> getMenuTreeList(@RequestParam("systemId")String systemId,@JudgeUserRole(role = "ADMIN") boolean isAdmin){
        DandelionUser user = SecurityUtils.getUser();
        List<SystemToMenuVo> systemToMenuVo = sysMenuService.getMenuTreeList(user.getUsername(),user.getRoles(),systemId,isAdmin);
        return new R<>(CommonConstants.SUCCESS, "查询成功", systemToMenuVo);
    }

    /**
     * 根据menuId获取菜单信息
     * @return
     */
    @GetMapping("/info/{menuId}")
    @SysLog("MenuId获取菜单信息")
//    @PreAuthorize("@mse.hasPermission('sys:menu:info')")
    public R getSysDeptById(@PathVariable Long menuId){
        SysMenu sysMenu = sysMenuService.getMenuByMenuId(menuId);
        if (sysMenu != null){
            return new R<>("查询成功",sysMenu);
        }
        return new R<>("没有这个菜单",null);
    }

    /**
     * 添加菜单
     */
    @PostMapping("/add")
    @SysLog("添加菜单")
    @PreAuthorize("@mse.hasPermission('sys:menu:add')")
    public R<SysMenu> addSysDept(@RequestBody @Valid SysMenu sysMenu){
        DandelionUser user = SecurityUtils.getUser();
        sysMenu.setCreateTime(LocalDateTime.now());
        sysMenu.setCreator(user.getRealName());
        SysMenu menu =sysMenuService.selectByName(sysMenu.getMenuName(),sysMenu.getPath());
        if(menu == null){
            if(sysMenuService.save(sysMenu)){
                return new R<>(CommonConstants.SUCCESS,"添加成功",sysMenuService.selectByName(sysMenu.getMenuName(),sysMenu.getPath()));
            }
            return new R<>(CommonConstants.FAIL,"添加失败",sysMenu);
        }
        if(menu.getDelFlag() == 1){
            sysMenu.setMenuId(menu.getMenuId());
            return updateSysDept(sysMenu);
        }else{
            return new R<>(CommonConstants.FAIL,
                    "请确认菜单名与路径是否正确，系统中已存在“"+ menu.getMenuName() +"”路径为“" + menu.getPath() + "”的菜单", menu);
        }

    }

    /**
     * 修改菜单
     */
    @PostMapping("/update")
    @SysLog("修改菜单")
    @PreAuthorize("@mse.hasPermission('sys:menu:update')")
    public R<SysMenu> updateSysDept(@RequestBody @Valid SysMenu sysMenu){
        DandelionUser user = SecurityUtils.getUser();
        sysMenu.setUpdator(user.getRealName());
        if(sysMenuService.updateMenuById(sysMenu)){
            return new R<>(CommonConstants.SUCCESS,"修改成功",sysMenu);
        }
        return new R<>(CommonConstants.FAIL,"修改失败",sysMenu);
    }
}
