package cn.icepear.dandelion.upm.biz.controller;

import cn.icepear.dandelion.common.core.constant.CommonConstants;
import cn.icepear.dandelion.common.core.utils.R;
import cn.icepear.dandelion.common.security.annotation.JudgeUserRole;
import cn.icepear.dandelion.upm.api.domain.entity.SysOauthClientDetails;
import cn.icepear.dandelion.upm.biz.service.SysOauthClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author rim-wood
 * @description 机构部门管理
 * @date Created on 2019-07-29.
 */
@RestController
@RequestMapping("/oauthClient")
public class OauthClientDetailsController {

    @Autowired
    private SysOauthClientDetailsService sysOauthClientDetailsService;


    /**
     * 查看客户端列表
     */
    @GetMapping("/list")
    @PreAuthorize("@mse.hasPermission('sys:client:list')")
    public R<List<SysOauthClientDetails>> getSysOauthClientDetails(@JudgeUserRole(role = "ADMIN") boolean isAdmin){
        if (!isAdmin){
            return new R<>(CommonConstants.FAIL,"非DAAS管理员不可查看", null);
        }
        return new R<>(CommonConstants.SUCCESS,"查询成功",sysOauthClientDetailsService.getAllList());
    }


    /**
     * 添加授权客户端
     */
    @PostMapping("/add")
    @PreAuthorize("@mse.hasPermission('sys:client:add')")
    public R<SysOauthClientDetails> addSysOauthClientDetails(@RequestBody @Valid SysOauthClientDetails sysOauthClientDetails, @JudgeUserRole(role = "ADMIN") boolean isAdmin){
        if (!isAdmin){
            return new R<>(CommonConstants.FAIL,"非DASS管理员不可添加",null);
        }
        if (sysOauthClientDetailsService.saveOauthClient(sysOauthClientDetails)){
            return new R<>(CommonConstants.SUCCESS,"成功添加客户端",sysOauthClientDetails);
        } else {
            return new R<>(CommonConstants.FAIL,"添加失败",sysOauthClientDetails);
        }

    }

    /**
     * 修改授权客户端
     */
    @PostMapping("/update")
    @PreAuthorize("@mse.hasPermission('sys:client:update')")
    public R<SysOauthClientDetails> updateSysOauthClientDetails(@RequestBody @Valid SysOauthClientDetails sysOauthClientDetails, @JudgeUserRole(role = "ADMIN") boolean isAdmin){
        if(!isAdmin){
            return new R<>(CommonConstants.FAIL,"非DAAS管理员不可修改客户端",null);
        }
        if(sysOauthClientDetailsService.updateClientDetailsById(sysOauthClientDetails)){
            return new R<>(CommonConstants.SUCCESS,"修改成功",sysOauthClientDetails);
        }else {
            return new R<>(CommonConstants.FAIL,"修改失败",sysOauthClientDetails);
        }
    }

}
