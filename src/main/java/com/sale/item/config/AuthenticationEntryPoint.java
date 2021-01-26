package com.sale.item.config;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.sale.item.exception.CustomAccessDeniedHandler;

@Component
public class AuthenticationEntryPoint extends BasicAuthenticationEntryPoint{
	public static final Logger logger = LoggerFactory.getLogger(AuthenticationEntryPoint.class);
	
	@Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx)
      throws IOException {
        response.addHeader("WWW-Authenticate", "Basic realm=" +getRealmName());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().println("HTTP Status 401 - " + authEx.getMessage());
    }

	@Override
    public void afterPropertiesSet(){
        setRealmName("DeveloperStack");
        super.afterPropertiesSet();
    }
}
