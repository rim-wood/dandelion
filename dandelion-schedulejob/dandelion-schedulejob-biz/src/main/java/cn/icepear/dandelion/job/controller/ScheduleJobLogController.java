package cn.icepear.dandelion.job.controller;

import cn.icepear.dandelion.common.core.utils.R;
import cn.icepear.dandelion.job.domain.entity.ScheduleJobLogEntity;
import cn.icepear.dandelion.job.service.ScheduleJobLogService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author rimwood
 * @date 2019-04-15
 * DAAS平台定时任务日志记录API
 */
@RestController
@RequestMapping("/jobLog")
public class ScheduleJobLogController {
	@Autowired
	private ScheduleJobLogService scheduleJobLogService;
	
	/**
	 * 定时任务日志列表
	 */
	@GetMapping("/list")
	@PreAuthorize("@mse.hasPermission('schedule:log:list')")
	public R list(Page page, String jobId){
		IPage rs = scheduleJobLogService.queryPage(page,jobId);
		return  new R<>("查询成功",rs);
	}
	
	/**
	 * 定时任务日志信息
	 */
	@GetMapping("/info/{logId}")
	@PreAuthorize("@mse.hasPermission('schedule:log:info')")
	public R info(@PathVariable("logId") Long logId){
		ScheduleJobLogEntity log = scheduleJobLogService.getById(logId);
		return  new R<>("查询成功",log);
	}
}
