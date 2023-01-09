package com.example.bankingapp.aspect;

import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.bankingapp.constant.Constant;
import com.example.bankingapp.dto.ResponseResource;

/**
 * Custom exception handler in banking application
 */
@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler{
	
	@org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
	public ResponseEntity<ResponseResource<String>> exception(Exception ex, WebRequest request){
		ResponseResource<String> responseResource = new ResponseResource<>(ex.getMessage(),Constant.FAILURE,null,MDC.get(Constant.MDC_TOKEN));
		return new ResponseEntity<>(responseResource,HttpStatus.INTERNAL_SERVER_ERROR);
		
	}

}
