package com.example.bankingapp.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import com.example.bankingapp.constant.Constant;
import com.example.bankingapp.utils.BankingUtils;

/**
 * Aspect used to log in controller methods
 *
 */
@Aspect
@Component
public class BankingAspect {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BankingAspect.class); 
	
	@Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
	public void controller() {
		
	}
	
	@Pointcut("within(@org.springframework.stereotype.Service *)")
	public void service() {
		
	}
	
	@Pointcut("execution(* *.*(..))")
	public void allMethod() {
		
	}
	
	/**
	 * Aspect for controller methods to log details
	 * 
	 * @param joinPoint
	 * @return Object
	 * @throws Throwable
	 */
	@Around("controller() && allMethod()")
	public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
		MDC.put(Constant.MDC_TOKEN, "BANK:"+BankingUtils.generateUid(12));
		LOGGER.info("Entering in method: {}",joinPoint.getSignature().getName());
		LOGGER.info("Class name: {}",joinPoint.getSignature().getDeclaringTypeName());
		Object result=joinPoint.proceed();
		LOGGER.info("Exiting  method: {}",joinPoint.getSignature().getName());
		return result;
	}
	
	/**
	 * Aspect for service methods to log details
	 * 
	 * @param joinPoint
	 * @return Object
	 * @throws Throwable
	 */
	@Around("service() && allMethod()")
	public Object logAroundService(ProceedingJoinPoint joinPoint) throws Throwable {
		MDC.put(Constant.MDC_TOKEN, "BANK:"+BankingUtils.generateUid(12));
		LOGGER.info("Entering in service method: {}",joinPoint.getSignature().getName());
		LOGGER.info("Class name: {}",joinPoint.getSignature().getDeclaringTypeName());
		Object result=joinPoint.proceed();
		LOGGER.info("Exiting  service method: {}",joinPoint.getSignature().getName());
		return result;
	}

}
