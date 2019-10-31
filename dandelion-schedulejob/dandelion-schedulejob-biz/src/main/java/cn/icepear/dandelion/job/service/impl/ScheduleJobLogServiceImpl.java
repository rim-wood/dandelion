package cn.icepear.dandelion.job.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stpass.cityplatform.hoslink.common.core.utils.StringUtils;
import com.stpass.cityplatform.hoslink.job.domain.entity.ScheduleJobLogEntity;
import com.stpass.cityplatform.hoslink.job.mapper.ScheduleJobLogMapper;
import com.stpass.cityplatform.hoslink.job.service.ScheduleJobLogService;
import org.springframework.stereotype.Service;

@Service("scheduleJobLogService")
public class ScheduleJobLogServiceImpl extends ServiceImpl<ScheduleJobLogMapper, ScheduleJobLogEntity> implements ScheduleJobLogService {

	@Override
	public IPage queryPage(Page page,String jobId) {
		return this.page(
				page,
				new QueryWrapper<ScheduleJobLogEntity>().like(StringUtils.isNotBlank(jobId),"job_id", jobId));
	}

}
