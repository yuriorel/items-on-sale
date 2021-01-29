package com.sale.item.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GeneralExceptionHandler {
	 @ExceptionHandler(value = Exception.class)
	  public ResponseEntity<String> defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
		 return new ResponseEntity<String>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
	 }
}
