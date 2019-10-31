package cn.icepear.dandelion.upm.biz.config;

import cn.icepear.dandelion.upm.biz.filter.RegisterFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author rim-wood
 * @description 过滤器配置
 * @date Created on 2019-09-09.
 */
@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean userRegisterFilter(){
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new RegisterFilter());
        registration.addUrlPatterns("/user/register");
        registration.setName("userRegisterFilter");
        registration.setOrder(1);
        return registration;
    }
}
