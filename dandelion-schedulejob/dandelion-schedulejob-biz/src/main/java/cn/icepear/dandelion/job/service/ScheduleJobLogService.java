package cn.icepear.dandelion.job.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.stpass.cityplatform.hoslink.job.domain.entity.ScheduleJobLogEntity;

/**
 * @author rimwood
 * @date 2019-04-15
 * 定时任务日志
 */
public interface ScheduleJobLogService extends IService<ScheduleJobLogEntity> {

	IPage queryPage(Page page,String jobId);
	
}
