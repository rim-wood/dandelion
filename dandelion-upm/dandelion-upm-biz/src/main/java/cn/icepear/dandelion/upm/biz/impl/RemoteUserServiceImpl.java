package cn.icepear.dandelion.upm.biz.impl;

import cn.icepear.dandelion.common.core.utils.R;
import cn.icepear.dandelion.upm.api.domain.dto.UserInfo;
import cn.icepear.dandelion.upm.api.domain.entity.SysUser;
import cn.icepear.dandelion.upm.api.remote.RemoteUserService;
import cn.icepear.dandelion.upm.biz.service.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author rim-wood
 * @description 内部用户服务实现
 * @date Created on 2019-04-26.
 */
@Service(version = "1.0.0")
@Slf4j
public class RemoteUserServiceImpl implements RemoteUserService {
    @Autowired
    private SysUserService sysUserService;
    @Override
    public R<UserInfo> info(String username, String from) {
        SysUser sysUser = sysUserService.getOne(new QueryWrapper<SysUser>().eq("user_name",username));
        if(sysUser!=null) {
            UserInfo userInfo = sysUserService.getUserInfo(sysUser);
            return new R<UserInfo>(userInfo);
        }
        return R.error(1000,"用户不存在",null);
    }
}
