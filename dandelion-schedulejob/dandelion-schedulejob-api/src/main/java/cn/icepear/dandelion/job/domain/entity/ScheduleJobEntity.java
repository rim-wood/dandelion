package cn.icepear.dandelion.job.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * @author rimwood
 * @date 2019-04-15
 * 定时任务实体
 */
@TableName("schedule_job")
@Data
public class ScheduleJobEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 任务调度参数key
	 */
    public static final String JOB_PARAM_KEY = "JOB_PARAM_KEY";
	
	/**
	 * 任务id
	 */
	@TableId
	private Long jobId;

	/**
	 * spring bean名称
	 */
	@NotBlank(message="bean名称不能为空")
	private String beanName;
	
	/**
	 * 方法名
	 */
	@NotBlank(message="方法名称不能为空")
	private String methodName;
	
	/**
	 * 参数
	 */
	private String params;
	
	/**
	 * cron表达式
	 */
	@NotBlank(message="cron表达式不能为空")
	private String cronExpression;

	/**
	 * 任务状态
	 */
	private Integer status;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 创建时间
	 */
	private Date createTime;

}
