package cn.icepear.dandelion.job.service.impl;

import cn.icepear.dandelion.common.core.utils.StringUtils;
import cn.icepear.dandelion.job.domain.entity.ScheduleJobLogEntity;
import cn.icepear.dandelion.job.mapper.ScheduleJobLogMapper;
import cn.icepear.dandelion.job.service.ScheduleJobLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
