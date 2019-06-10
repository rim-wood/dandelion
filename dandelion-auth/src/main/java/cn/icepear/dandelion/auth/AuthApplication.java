package cn.icepear.dandelion.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author rim-wood
 * @description Oauth2 鉴权服务
 * @date Created on 2019-04-29.
 */
@SpringBootApplication(scanBasePackages = "cn.icepear.dandelion")
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }
}
