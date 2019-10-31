package cn.icepear.dandelion.job.utils;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import cn.icepear.dandelion.job.domain.entity.ScheduleJobEntity;
import cn.icepear.dandelion.job.domain.entity.ScheduleJobLogEntity;
import cn.icepear.dandelion.job.service.ScheduleJobLogService;
import com.stpass.cityplatform.hoslink.common.core.utils.SpringContextHolder;
import com.stpass.cityplatform.hoslink.common.core.utils.StringUtils;
import com.stpass.cityplatform.hoslink.job.domain.entity.ScheduleJobEntity;
import com.stpass.cityplatform.hoslink.job.domain.entity.ScheduleJobLogEntity;
import com.stpass.cityplatform.hoslink.job.service.ScheduleJobLogService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;
import java.util.concurrent.*;


/**
 * @author rimwood
 * @date 2019-04-15
 * 定时任务
 */
@Slf4j
public class ScheduleJob extends QuartzJobBean {
	ThreadFactory namedThreadFactory =  ThreadFactoryBuilder.create().setNamePrefix("job-pool-%d").build();
	private ExecutorService pool =  new ThreadPoolExecutor(1, 1,
					0L, TimeUnit.MILLISECONDS,
					new LinkedBlockingQueue<Runnable>(),namedThreadFactory,new ThreadPoolExecutor.AbortPolicy());
	
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        ScheduleJobEntity scheduleJob = (ScheduleJobEntity) context.getMergedJobDataMap()
        		.get(ScheduleJobEntity.JOB_PARAM_KEY);
        
        //获取spring bean
        ScheduleJobLogService scheduleJobLogService = (ScheduleJobLogService) SpringContextHolder.getBean("scheduleJobLogService");
        
        //数据库保存执行记录
        ScheduleJobLogEntity scheduleJobLog = new ScheduleJobLogEntity();
		BeanUtils.copyProperties(scheduleJob,scheduleJobLog);
		scheduleJobLog.setCreateTime(new Date());
        
        //任务开始时间
        long startTime = System.currentTimeMillis();
        
        try {
            //执行任务
        	log.info("任务准备执行，任务ID：" + scheduleJob.getJobId());
            ScheduleRunnable task = new ScheduleRunnable(scheduleJob.getBeanName(),
            		scheduleJob.getMethodName(), scheduleJob.getParams());
            Future<?> future = pool.submit(task);
            
			future.get();
			
			//任务执行总时长
			long times = System.currentTimeMillis() - startTime;
			scheduleJobLog.setTimes((int)times);
			//任务状态    0：成功    1：失败
			scheduleJobLog.setStatus(0);

			log.info("任务执行完毕，任务ID：" + scheduleJob.getJobId() + "  总共耗时：" + times + "毫秒");
		} catch (Exception e) {
			log.error("任务执行失败，任务ID：" + scheduleJob.getJobId(), e);
			
			//任务执行总时长
			long times = System.currentTimeMillis() - startTime;
			scheduleJobLog.setTimes((int)times);
			
			//任务状态    0：成功    1：失败
			scheduleJobLog.setStatus(1);
			scheduleJobLog.setError(StringUtils.substring(e.toString(), 0, 2000));
		}finally {
        	//关闭线程池
        	pool.shutdown();
			scheduleJobLogService.save(scheduleJobLog);
		}
    }
}
