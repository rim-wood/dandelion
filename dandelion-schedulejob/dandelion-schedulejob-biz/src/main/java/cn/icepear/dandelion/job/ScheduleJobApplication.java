package cn.icepear.dandelion.job;

import cn.icepear.dandelion.common.security.annotation.EnableDandelionResourceServer;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * @author rimwood
 * @date 2019-04-15
 * DAAS平台定时任务服务
 */
@EnableDubbo
@ImportResource({"classpath:dubbo/dandelion_schedulejob_dubbo.xml","${spring.dubbo.profile}"})
@SpringBootApplication(scanBasePackages = "cn.icepear.dandelion")
@EnableDandelionResourceServer
public class ScheduleJobApplication {
	public static void main(String[] args) {
		SpringApplication.run(ScheduleJobApplication.class, args);
	}

}
