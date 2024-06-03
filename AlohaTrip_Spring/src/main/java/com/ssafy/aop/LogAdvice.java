package com.ssafy.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


@Aspect
@Slf4j
@Component
public class LogAdvice {

    @Around("execution(* com.ssafy.controller..*(..))")
    public Object logTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.info("========================");
        long start = System.currentTimeMillis();

        log.info("Target: " + proceedingJoinPoint.getTarget());
        Object result = null;

        try {
            result = proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            log.error("Exception in " + proceedingJoinPoint.getSignature(), e);
            throw e;
        }

        long end = System.currentTimeMillis();
        log.info("API 응답 시간: " + (end - start) + " ms");
        log.info("========================");

        return result;
    }

//    @AfterThrowing(pointcut = "execution(* com.ssafy..*(..))", throwing = "exception")
//    public void logException(Exception exception) {
//        log.info("Exception Found");
//        log.info("exception: " + exception);
//    }
}
