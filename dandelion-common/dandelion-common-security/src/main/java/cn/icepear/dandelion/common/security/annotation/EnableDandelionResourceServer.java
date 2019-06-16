package cn.icepear.dandelion.common.security.annotation;

import cn.icepear.dandelion.common.security.component.resource.server.DandelionSecurityBeanDefinitionRegistrar;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import java.lang.annotation.*;

/**
 * @author rim-wood
 * @description 开启资源服务配置
 * @date Created on 2019/6/11.
 */
@Documented
@Inherited
@EnableResourceServer
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import({DandelionSecurityBeanDefinitionRegistrar.class})
public @interface EnableDandelionResourceServer {
}
