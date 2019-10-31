package cn.icepear.dandelion.upm.api.remote;


import cn.icepear.dandelion.common.core.utils.R;
import cn.icepear.dandelion.upm.api.domain.entity.SysLog;

/**
 * @author rim-wood
 * @description 日志远程调用
 * @date Created on 2019-08-05.
 */
public interface RemoteLogService {
    /**
     * 记录日志
     * @param sysLog
     * @return
     */
    R saveLog(SysLog sysLog);
}
