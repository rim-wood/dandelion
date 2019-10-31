package cn.icepear.dandelion.job.mapper;

import cn.icepear.dandelion.job.domain.entity.ScheduleJobLogEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stpass.cityplatform.hoslink.job.domain.entity.ScheduleJobLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author rimwood
 * @date 2019-04-15
 * 定时任务日志Mapper
 */
@Mapper
public interface ScheduleJobLogMapper extends BaseMapper<ScheduleJobLogEntity> {
	
}
