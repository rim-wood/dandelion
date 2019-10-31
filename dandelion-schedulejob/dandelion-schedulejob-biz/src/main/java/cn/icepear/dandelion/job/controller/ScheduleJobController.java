package cn.icepear.dandelion.job.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stpass.cityplatform.hoslink.common.core.constant.CommonConstants;
import com.stpass.cityplatform.hoslink.common.core.utils.R;
import com.stpass.cityplatform.hoslink.common.log.annotation.SysLog;
import com.stpass.cityplatform.hoslink.job.domain.entity.ScheduleJobEntity;
import com.stpass.cityplatform.hoslink.job.service.ScheduleJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author rimwood
 * @date 2019-04-15
 * DAAS平台定时任务API
 */
@RestController
@RequestMapping("/job")
public class ScheduleJobController {
	@Autowired
	private ScheduleJobService scheduleJobService;
	
	/**
	 * 定时任务列表
	 */
	@GetMapping("/list")
	@PreAuthorize("@mse.hasPermission('schedule:job:list')")
	public R list(Page page, String beanName){
		IPage rs = scheduleJobService.queryPage(page,beanName);
		return  new R<>("查询成功",rs);
	}
	
	/**
	 * 定时任务信息
	 */
	@GetMapping("/info/{jobId}")
	@PreAuthorize("@mse.hasPermission('schedule:job:info')")
	public R info(@PathVariable("jobId") Long jobId){
		ScheduleJobEntity schedule = scheduleJobService.getById(jobId);
		
		 return new R<>(schedule);
	}
	
	/**
	 * 保存定时任务
	 */
	@SysLog("保存定时任务")
	@PostMapping("/save")
	@PreAuthorize("@mse.hasPermission('schedule:job:save')")
	public R save(@RequestBody ScheduleJobEntity scheduleJob){
		scheduleJobService.save(scheduleJob);
		return R.builder().code(CommonConstants.SUCCESS).msg("添加成功").build();
	}
	
	/**
	 * 修改定时任务
	 */
	@SysLog("修改定时任务")
	@PostMapping("/update")
	@PreAuthorize("@mse.hasPermission('schedule:job:update')")
	public R update(@RequestBody ScheduleJobEntity scheduleJob){
		scheduleJobService.update(scheduleJob);
		return R.builder().code(CommonConstants.SUCCESS).msg("更新成功").build();
	}
	
	/**
	 * 删除定时任务
	 */
	@SysLog("删除定时任务")
	@PostMapping("/delete")
	@PreAuthorize("@mse.hasPermission('schedule:job:delete')")
	public R delete(@RequestBody Long[] jobIds){
		scheduleJobService.deleteBatch(jobIds);
		return R.builder().code(CommonConstants.SUCCESS).msg("删除成功").build();
	}
	
	/**
	 * 立即执行任务
	 */
	@SysLog("立即执行任务")
	@PostMapping("/run")
	@PreAuthorize("@mse.hasPermission('schedule:job:run')")
	public R run(@RequestBody Long[] jobIds){
		scheduleJobService.run(jobIds);
		return R.builder().code(CommonConstants.SUCCESS).msg("执行成功").build();
	}
	
	/**
	 * 暂停定时任务
	 */
	@SysLog("暂停定时任务")
	@PostMapping("/pause")
	@PreAuthorize("@mse.hasPermission('schedule:job:pause')")
	public R pause(@RequestBody Long[] jobIds){
		scheduleJobService.pause(jobIds);
		return R.builder().code(CommonConstants.SUCCESS).msg("任务已暂停").build();
	}
	
	/**
	 * 恢复定时任务
	 */
	@SysLog("恢复定时任务")
	@PostMapping("/resume")
	@PreAuthorize("@mse.hasPermission('schedule:job:resume')")
	public R resume(@RequestBody Long[] jobIds){
		scheduleJobService.resume(jobIds);
		return R.builder().code(CommonConstants.SUCCESS).msg("任务已恢复").build();
	}

}
