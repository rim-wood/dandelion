package cn.icepear.dandelion.auth.config;

import cn.icepear.dandelion.common.security.component.error.DandelionAuthExceptionEntryPoint;
import cn.icepear.dandelion.common.security.component.error.DandelionOAuth2AccessDeniedHandler;
import cn.icepear.dandelion.common.security.service.DandelionUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

/**
 * @author rimwood
 * @date 2019/2/1
 * 认证相关配置
 */
@Configuration
@EnableWebSecurity
@Order(90)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private DandelionUserDetailsService userDetailsService;
	@Lazy
	@Autowired
	private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;

	@Override
	@SneakyThrows
	protected void configure(HttpSecurity http) {
		http
			.authorizeRequests()
			.antMatchers(
				"/actuator/**").permitAll()
			.anyRequest().authenticated()
			.and().formLogin()
			.and().csrf().disable();
	}

	/**
	 * 注入自定义的userDetailsService实现，获取用户信息，设置密码加密方式
	 *
	 * @param authenticationManagerBuilder
	 * @throws Exception
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder
				.userDetailsService(userDetailsService)
				.passwordEncoder(passwordEncoder());
	}

	@Bean
	@Override
	@SneakyThrows
	public AuthenticationManager authenticationManagerBean() {
		return super.authenticationManagerBean();
	}

//	@Bean
//	public AuthenticationSuccessHandler mobileLoginSuccessHandler() {
//		return MobileLoginSuccessHandler.builder()
//			.objectMapper(objectMapper)
//			.clientDetailsService(hoslinkClientDetailsService)
//			.passwordEncoder(passwordEncoder())
//			.defaultAuthorizationServerTokenServices(defaultAuthorizationServerTokenServices).build();
//	}


	/**
	 * https://spring.io/blog/2017/11/01/spring-security-5-0-0-rc1-released#password-storage-updated
	 * Encoded password does not look like BCrypt
	 *
	 * @return PasswordEncoder
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

}
