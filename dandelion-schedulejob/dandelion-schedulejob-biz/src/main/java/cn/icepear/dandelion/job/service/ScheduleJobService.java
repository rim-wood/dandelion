package cn.icepear.dandelion.job.service;

import cn.icepear.dandelion.job.domain.entity.ScheduleJobEntity;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author rimwood
 * @date 2019-04-15
 * 定时任务
 */
public interface ScheduleJobService extends IService<ScheduleJobEntity> {

	IPage queryPage(Page page, String beanName);
	
	/**
	 * 更新定时任务
	 */
	void update(ScheduleJobEntity scheduleJob);
	
	/**
	 * 批量删除定时任务
	 */
	void deleteBatch(Long[] jobIds);

	/**
	 * 根据方法名称和参数删除定时任务
	 * @param mothodName
	 * @param params
	 */
	void delete(String mothodName, String params);
	
	/**
	 * 批量更新定时任务状态
	 */
	int updateBatch(Long[] jobIds, int status);
	
	/**
	 * 立即执行
	 */
	void run(Long[] jobIds);
	
	/**
	 * 暂停运行
	 */
	void pause(Long[] jobIds);
	
	/**
	 * 恢复运行
	 */
	void resume(Long[] jobIds);
}
