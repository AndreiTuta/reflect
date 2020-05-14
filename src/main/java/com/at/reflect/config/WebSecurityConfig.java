package com.at.reflect.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private JwtAuthenticationEntryPoint authorizedHandler;

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		// Stateless config
		http.csrf().disable()
				// don't create session
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				// handle any exceptions
				.exceptionHandling().authenticationEntryPoint(authorizedHandler).and()
				// allow unauthenticated access to login
				.authorizeRequests().antMatchers("/lounge/login").anonymous();

		http.addFilterBefore(tenantFilter, WebAsyncManagerIntegrationFilter.class)
				// after the user logged in, validate the token
				.addFilterBefore(loungeFilter, WebAsyncManagerIntegrationFilter.class)
				.addFilterBefore(loungeDashboardFilter, WebAsyncManagerIntegrationFilter.class).formLogin()
				.loginPage("/lounge/login").successHandler(loungeAuthenticationSuccessHandler())
				.failureHandler(customAuthenticationFailureHandler()).permitAll().and().logout()
				.addLogoutHandler(loungeLogoutHandler()).permitAll();
	}

	@Bean
	public AuthenticationSuccessHandler loungeAuthenticationSuccessHandler() {
		return new LoungeAuthenticationSuccessHandler();
	}

	@Bean
	public AuthenticationFailureHandler customAuthenticationFailureHandler() {
		return new LoungeAuthenticationFailureHandler();
	}

	@Bean
	public LoungeLogoutSuccessHandler loungeLogoutHandler() {
		return new LoungeLogoutSuccessHandler();
	}

	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(service);
		authProvider.setPasswordEncoder(new BCryptPasswordEncoder());
		return authProvider;
	}
}