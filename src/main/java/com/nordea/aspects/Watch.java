package com.nordea.aspects;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class Watch {

	@Around("@annotation(com.nordea.aspects.Timer)")
	public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
		System.out.println("Start processing file.");
		LocalDateTime start = LocalDateTime.now();

		Object proceed = joinPoint.proceed();

		long elapsedTime = start.until(LocalDateTime.now(), ChronoUnit.MILLIS);
		System.out.println("Finish processing file. It took " + elapsedTime + " ms to process the file.");

		return proceed;
	}
}
