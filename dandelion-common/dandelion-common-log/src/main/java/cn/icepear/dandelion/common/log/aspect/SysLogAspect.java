package cn.icepear.dandelion.common.log.aspect;

import cn.icepear.dandelion.common.core.utils.SpringContextHolder;
import cn.icepear.dandelion.common.log.annotation.SysLog;
import cn.icepear.dandelion.common.log.event.SysLogEvent;
import cn.icepear.dandelion.common.log.util.SysLogUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 操作日志使用spring event异步入库
 * @author rimwood
 */
@Aspect
@Slf4j
@Component
public class SysLogAspect {

	@Around("@annotation(sysLog)")
	public Object around(ProceedingJoinPoint point, SysLog sysLog) throws Throwable {
		String strClassName = point.getTarget().getClass().getName();
		String strMethodName = point.getSignature().getName();
		log.debug("[类名]:{},[方法]:{}", strClassName, strMethodName);

		cn.icepear.dandelion.upm.api.domain.entity.SysLog logVo = SysLogUtils.getSysLog();
		logVo.setTitle(sysLog.value());
		// 发送异步日志事件
		Long startTime = System.currentTimeMillis();
		Object obj = point.proceed();
		Long endTime = System.currentTimeMillis();
		logVo.setTime(endTime - startTime);
		SpringContextHolder.publishEvent(new SysLogEvent(this,logVo));
		return obj;
	}

}
