package cn.icepear.dandelion.upm.biz;


import cn.icepear.dandelion.common.security.annotation.EnableDandelionResourceServer;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * @author rimwood
 * @date 2019-04-15
 * 用户管理服务
 */

@EnableDubbo
@SpringBootApplication(scanBasePackages = "cn.icepear.dandelion")
@ImportResource({"classpath:dubbo/hoslink_upm_dubbo.xml","${spring.dubbo.profile}"})
@EnableDandelionResourceServer
@EnableHystrix
public class UpmApplication {
	public static void main(String[] args) {
		SpringApplication.run(UpmApplication.class, args);
	}

}
