package cn.icepear.dandelion.authorization.endpoint;

import cn.icepear.dandelion.authorization.entity.OauthClientDetails;
import cn.icepear.dandelion.authorization.service.SysOauthClientDetailsService;
import cn.icepear.dandelion.common.core.constant.CommonConstants;
import cn.icepear.dandelion.common.core.utils.R;
import cn.icepear.dandelion.common.security.annotation.JudgeUserRole;
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
    public R<List<OauthClientDetails>> getSysOauthClientDetails(@JudgeUserRole(role = "ADMIN") boolean isAdmin){
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
    public R<OauthClientDetails> addSysOauthClientDetails(@RequestBody @Valid OauthClientDetails oauthClientDetails, @JudgeUserRole(role = "ADMIN") boolean isAdmin){
        if (!isAdmin){
            return new R<>(CommonConstants.FAIL,"非DASS管理员不可添加",null);
        }
        if (sysOauthClientDetailsService.saveOauthClient(oauthClientDetails)){
            return new R<>(CommonConstants.SUCCESS,"成功添加客户端", oauthClientDetails);
        } else {
            return new R<>(CommonConstants.FAIL,"添加失败", oauthClientDetails);
        }

    }

    /**
     * 修改授权客户端
     */
    @PostMapping("/update")
    @PreAuthorize("@mse.hasPermission('sys:client:update')")
    public R<OauthClientDetails> updateSysOauthClientDetails(@RequestBody @Valid OauthClientDetails oauthClientDetails, @JudgeUserRole(role = "ADMIN") boolean isAdmin){
        if(!isAdmin){
            return new R<>(CommonConstants.FAIL,"非DAAS管理员不可修改客户端",null);
        }
        if(sysOauthClientDetailsService.updateClientDetailsById(oauthClientDetails)){
            return new R<>(CommonConstants.SUCCESS,"修改成功", oauthClientDetails);
        }else {
            return new R<>(CommonConstants.FAIL,"修改失败", oauthClientDetails);
        }
    }

}
