package cn.icepear.dandelion.common.security.component.resource.server;

import cn.icepear.dandelion.common.core.config.FilterIgnorePropertiesConfig;
import cn.icepear.dandelion.common.security.component.DandelionUserAuthenticationConverter;
import cn.icepear.dandelion.common.security.component.error.DandelionOAuth2AccessDeniedHandler;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;

/**
 * @author rim-wood
 * @description
 * @date Created on 2019/6/11.
 */
public class DandelionResourceServerConfigurerAdapter extends ResourceServerConfigurerAdapter {
    @Autowired
    protected ResourceAuthExceptionEntryPoint resourceAuthExceptionEntryPoint;
    @Autowired
    protected DandelionRemoteTokenServices dandelionRemoteTokenServices;
    @Autowired
    private FilterIgnorePropertiesConfig ignorePropertiesConfig;
    @Autowired
    private DandelionOAuth2AccessDeniedHandler dandelionOAuth2AccessDeniedHandler;

    /**
     * 默认的配置，对外暴露
     *
     * @param httpSecurity
     */
    @Override
    @SneakyThrows
    public void configure(HttpSecurity httpSecurity) {
        //允许使用iframe 嵌套，避免swagger-ui 不被加载的问题
        httpSecurity.headers().frameOptions().disable();
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>
                .ExpressionInterceptUrlRegistry registry = httpSecurity
                .authorizeRequests();
        ignorePropertiesConfig.getUrls()
                .forEach(url -> registry.antMatchers(url).permitAll());
        registry.anyRequest().authenticated()
                .and().csrf().disable();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
        UserAuthenticationConverter userTokenConverter = new DandelionUserAuthenticationConverter();
        accessTokenConverter.setUserTokenConverter(userTokenConverter);

        dandelionRemoteTokenServices.setTokenConverter(accessTokenConverter);
        resources.authenticationEntryPoint(resourceAuthExceptionEntryPoint)
                .accessDeniedHandler(dandelionOAuth2AccessDeniedHandler)
                .tokenServices(dandelionRemoteTokenServices);
    }
}
