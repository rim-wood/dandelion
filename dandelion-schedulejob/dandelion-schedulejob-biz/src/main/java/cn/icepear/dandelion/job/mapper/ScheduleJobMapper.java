package cn.icepear.dandelion.job.mapper;

import cn.icepear.dandelion.job.domain.entity.ScheduleJobEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stpass.cityplatform.hoslink.job.domain.entity.ScheduleJobEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * @author rimwood
 * @date 2019-04-15
 * 定时任务Mapper
 */
@Mapper
public interface ScheduleJobMapper extends BaseMapper<ScheduleJobEntity> {
	
	/**
	 * 批量更新状态
	 */
	int updateBatch(Map<String, Object> map);
}
