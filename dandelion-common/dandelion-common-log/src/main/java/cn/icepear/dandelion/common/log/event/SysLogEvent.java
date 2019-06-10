package cn.icepear.dandelion.common.log.event;

import cn.icepear.dandelion.common.log.domain.SysLogEntity;
import org.springframework.context.ApplicationEvent;

/**
 * @author lengleng
 * 系统日志事件
 */
public class SysLogEvent extends ApplicationEvent {

	public SysLogEvent(SysLogEntity source) {
		super(source);
	}
}
