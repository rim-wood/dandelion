package cn.icepear.dandelion.job.utils;

import com.stpass.cityplatform.hoslink.common.core.exception.CustomException;
import com.stpass.cityplatform.hoslink.common.core.utils.SpringContextHolder;
import com.stpass.cityplatform.hoslink.common.core.utils.StringUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * @author rimwood
 * @date 2019-04-15
 * 定时任务Runnable执行任务
 */
public class ScheduleRunnable implements Runnable {
	private Object target;
	private Method method;
	private String params;
	
	public ScheduleRunnable(String beanName, String methodName, String params) throws NoSuchMethodException, SecurityException {
		this.target = SpringContextHolder.getBean(beanName);
		this.params = params;
		
		if(StringUtils.isNotBlank(params)){
			this.method = target.getClass().getDeclaredMethod(methodName, String.class);
		}else{
			this.method = target.getClass().getDeclaredMethod(methodName);
		}
	}

	@Override
	public void run() {
		try {
			ReflectionUtils.makeAccessible(method);
			if(StringUtils.isNotBlank(params)){
				method.invoke(target, params);
			}else{
				method.invoke(target);
			}
		}catch (Exception e) {
			throw new CustomException("执行定时任务失败", e);
		}
	}

}
