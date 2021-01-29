package com.sale.item.exception;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.csrf.CsrfToken;

public class CustomAccessDeniedHandler implements AccessDeniedHandler{
	public static final Logger logger = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);
	
    protected static final String REQUEST_ATTRIBUTE_NAME = "_csrf";
    protected static final String RESPONSE_HEADER_NAME = "X-CSRF-HEADER";
    protected static final String RESPONSE_PARAM_NAME = "X-CSRF-PARAM";
    protected static final String RESPONSE_TOKEN_NAME = "X-CSRF-TOKEN";

  @Override
  public void handle(
    HttpServletRequest request,
    HttpServletResponse response, 
    AccessDeniedException exc) throws IOException, ServletException {
      
      Authentication auth 
        = SecurityContextHolder.getContext().getAuthentication();
      if (auth != null) {
    	  logger.warn(String.format("User: %s attempted to access the protected URL: %s", auth.getName(), request.getRequestURI()));
      }
      
      SecurityContextHolder.getContext().setAuthentication(null);
      
/*      CsrfToken token = (CsrfToken) request.getAttribute(REQUEST_ATTRIBUTE_NAME);

      if (token != null) {
          response.setHeader(RESPONSE_HEADER_NAME, token.getHeaderName());
          response.setHeader(RESPONSE_PARAM_NAME, token.getParameterName());
          response.setHeader(RESPONSE_TOKEN_NAME, token.getToken());
      }
*/
      response.sendError(HttpServletResponse.SC_FORBIDDEN,"Access denied");
  }
}
