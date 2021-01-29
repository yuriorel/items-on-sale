package com.sale.item.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.sale.item.exception.CustomAccessDeniedHandler;

@EnableWebSecurity
public class SecurityAdapter extends WebSecurityConfigurerAdapter {
	@Autowired
	private AuthenticationEntryPoint authEntryPoint;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		   .addFilterAfter(new CsrfTokenResponseHeaderBindingFilter(), CsrfFilter.class)
		   .cors()
		   .and()
		   .authorizeRequests()
           .anyRequest().access("hasRole('ADVANCEDUSER')")
           .and()
           .exceptionHandling().accessDeniedHandler(accessDeniedHandler())
           .and()
           .httpBasic()
		   .authenticationEntryPoint(authEntryPoint); 
}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("user").password("{noop}user").roles("USER");
		auth.inMemoryAuthentication().withUser("customer").password("{noop}customer").roles("ADVANCEDUSER");
		//auth.inMemoryAuthentication().withUser("superadmin").password("{noop}superadmin").roles("SUPERADMIN");
	}
	
	@Bean
    CorsConfigurationSource corsConfigurationSource() 
    {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("https://shopping.rbc.com"));
        configuration.setAllowedMethods(Arrays.asList("GET","OPTIONS","POST"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/items-on-sale/**", configuration);
        return source;
    }
	
	@Bean
	public AccessDeniedHandler accessDeniedHandler(){
	    return new CustomAccessDeniedHandler();
	}
}
