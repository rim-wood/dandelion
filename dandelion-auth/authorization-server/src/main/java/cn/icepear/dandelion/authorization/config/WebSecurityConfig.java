package cn.icepear.dandelion.authorization.config;

import cn.icepear.dandelion.common.security.service.DandelionUserDetailsService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
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
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DandelionUserDetailsService userDetailsService;
	@Lazy
	@Autowired
	private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	@SneakyThrows
	protected void configure(HttpSecurity http) {
		http
				.authorizeRequests()
				.antMatchers(
						"/actuator/**", "/oauth/logout", "/**.css", "/**.js", "/images/**.png").permitAll()
				.anyRequest().authenticated()
				.and().formLogin().loginPage("/login").permitAll()
//				.and().formLogin()
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
				.passwordEncoder(passwordEncoder);
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




}
