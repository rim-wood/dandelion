package cn.icepear.dandelion.job.task;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.stereotype.Component;

/**
 * 测试定时任务(演示Demo，可删除)
 *
 * testTask为spring bean的名称
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.2.0 2016-11-28
 */
@Component("testTask")
@Slf4j
public class TestTask {


	public void test(String params){
		log.info("我是带参数的test方法，正在被执行，参数为：" + params);
	}
	
	
	public void test2(){
		log.info("我是不带参数的test2方法，正在被执行");
	}
}
