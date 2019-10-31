package cn.icepear.dandelion.upm.biz.remote.impl;
import cn.icepear.dandelion.common.core.constant.CommonConstants;
import cn.icepear.dandelion.common.core.utils.R;
import cn.icepear.dandelion.upm.api.domain.entity.SysLog;
import cn.icepear.dandelion.upm.api.remote.RemoteLogService;
import cn.icepear.dandelion.upm.biz.service.SysLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author rim-wood
 * @description daas日志记录远程调用实现
 * @date Created on 2019-08-06.
 */
@Slf4j
public class RemoteLogServiceImpl implements RemoteLogService {

    @Autowired
    private SysLogService sysLogService;

    @Override
    public R saveLog(SysLog sysLog) {
         boolean result = sysLogService.save(sysLog);
         if(result) {
             return new R(CommonConstants.SUCCESS,"保存成功",null);
         }
         return R.error(1000,"保存失败",null);
    }
}
