package com.at.reflect.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	private static String REALM = "REFLECT_REALM";
	private static final String USER = "reflect-user";
	private static final String RAW_PASSWORD = "user1Pass";
	private static final String ADMIN_ROLE = "ADMIN";
	private static final String API_ALL = "/api/**";

	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.inMemoryAuthentication().withUser(USER).password(passwordEncoder().encode(RAW_PASSWORD))
				.roles(ADMIN_ROLE);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable().authorizeRequests().antMatchers(API_ALL).hasRole(ADMIN_ROLE).and().httpBasic()
				.realmName(REALM).authenticationEntryPoint(getBasicAuthEntryPoint()).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
	}

	@Bean
	public AuthEntryPoint getBasicAuthEntryPoint() {
		return new AuthEntryPoint();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}