package cn.icepear.dandelion.upm.biz.config;

import cn.icepear.dandelion.common.security.component.resolver.JudgeUserRoleHandlerMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * MVC配置
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-04-20 22:30
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer{
    @Autowired
    private JudgeUserRoleHandlerMethodArgumentResolver judgeUserRoleHandlerMethodArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(judgeUserRoleHandlerMethodArgumentResolver);
    }
}