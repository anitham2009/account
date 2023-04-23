package com.app.account.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
	

@Configuration	
public class SecurityConfiguration  {

		@Bean
	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	      
	        http
	        .authorizeRequests().anyRequest().permitAll()
	        .and().headers().frameOptions().disable()
	        .and().httpBasic().disable()
	        .csrf().disable();
	        return http.build();
	    }
	 
	
}