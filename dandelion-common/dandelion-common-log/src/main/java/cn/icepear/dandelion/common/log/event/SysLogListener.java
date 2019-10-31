package cn.icepear.dandelion.common.log.event;

import cn.icepear.dandelion.upm.api.domain.entity.SysLog;
import cn.icepear.dandelion.upm.api.remote.RemoteLogService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


/**
 * @author lengleng
 * 异步监听日志事件
 */
@Slf4j
@AllArgsConstructor
@Component
public class SysLogListener {
	@Autowired
	private RemoteLogService remoteLogService;

	@Async
	@EventListener
	public void saveSysLog(SysLogEvent event) {
		SysLog sysLog = event.getSysLog();
		remoteLogService.saveLog(sysLog);
	}
}
