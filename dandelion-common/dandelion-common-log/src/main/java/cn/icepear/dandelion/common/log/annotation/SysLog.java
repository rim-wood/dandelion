package cn.icepear.dandelion.common.log.annotation;

import java.lang.annotation.*;

/**
 * @author rimwood
 * @date 2019-04-01
 * 操作日志注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

	/**
	 * 描述
	 *
	 * @return {String}
	 */
	String value();
}
