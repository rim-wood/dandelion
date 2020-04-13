package cn.icepear.dandelion.job.service;

import cn.icepear.dandelion.job.domain.entity.ScheduleJobLogEntity;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author rimwood
 * @date 2019-04-15
 * 定时任务日志
 */
public interface ScheduleJobLogService extends IService<ScheduleJobLogEntity> {

	IPage queryPage(Page page,String jobId);
	
}
