package cn.icepear.dandelion.authorization;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ImportResource;

/**
 * @author rim-wood
 * @description Oauth2 鉴权服务
 * @date Created on 2019-04-29.
 */
@EnableDubbo
@ImportResource(locations = {"classpath:dubbo/dandelion_auth_dubbo.xml","${spring.dubbo.profile}"})
@SpringBootApplication
@ComponentScan(basePackages = "cn.icepear.dandelion",excludeFilters = {
        @ComponentScan.Filter(type = FilterType.REGEX, pattern = "cn.icepear.dandelion.common.security.component.resource.server.*"),})
@EnableHystrix
@EnableDiscoveryClient
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }
}
