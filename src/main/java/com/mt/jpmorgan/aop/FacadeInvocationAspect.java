package com.mt.jpmorgan.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class FacadeInvocationAspect {

	private final static Logger LOG = LoggerFactory.getLogger(FacadeInvocationAspect.class);

	@Pointcut("execution(* com.mt.jpmorgan.facade..*.*(..))")
	public void auditFacadeExecution() {
		// Empty
	}

	@Around("auditFacadeExecution()")
	public Object audit(ProceedingJoinPoint pjp) throws Throwable {
		// TODO Audit Use case facade execution log.
		LOG.info("Facade executed");
		return pjp.proceed();
	}
}
