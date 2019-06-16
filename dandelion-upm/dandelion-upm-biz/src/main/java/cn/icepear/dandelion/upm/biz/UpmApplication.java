package cn.icepear.dandelion.upm.biz;


import cn.icepear.dandelion.common.security.annotation.EnableDandelionResourceServer;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * @author rimwood
 * @date 2019-04-15
 * 用户管理服务
 */
@EnableDubbo
@SpringBootApplication(scanBasePackages = "cn.icepear.dandelion")
@EnableDandelionResourceServer
public class UpmApplication {
	public static void main(String[] args) {
		SpringApplication.run(UpmApplication.class, args);
	}

}
