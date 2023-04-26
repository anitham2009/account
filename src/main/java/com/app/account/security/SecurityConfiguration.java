package com.app.account.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration of application.
 * @author Anitha Manoharan
 *
 */
@Configuration
public class SecurityConfiguration {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/swagger-ui/**").permitAll().and().headers().frameOptions().disable()
				.and().httpBasic().disable().csrf().disable();
		return http.build();
	}

}
