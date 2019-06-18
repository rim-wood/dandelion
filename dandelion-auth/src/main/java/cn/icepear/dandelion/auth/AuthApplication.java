package cn.icepear.dandelion.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * @author rim-wood
 * @description Oauth2 鉴权服务
 * @date Created on 2019-04-29.
 */
@SpringBootApplication
@ComponentScan(basePackages = "cn.icepear.dandelion",excludeFilters = {@ComponentScan.Filter(type = FilterType.REGEX, pattern = "cn.icepear.dandelion.common.security.component.resource.server.*"),})
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }
}
