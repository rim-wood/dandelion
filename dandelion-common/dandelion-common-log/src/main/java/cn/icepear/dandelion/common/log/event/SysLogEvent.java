package cn.icepear.dandelion.common.log.event;

import cn.icepear.dandelion.upm.api.domain.entity.SysLog;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author rimwood
 * 系统日志事件
 */
@Getter
public class SysLogEvent extends ApplicationEvent {

	private SysLog sysLog;

	public SysLogEvent(Object source,SysLog sysLog) {
		super(source);
		this.sysLog = sysLog;
	}
}
