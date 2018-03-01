package com.spring.example.aspect.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Aspect
@Configuration
public class LoggingAspect {

    private Logger logger = LoggerFactory.getLogger("cache");

    @Around("execution(* com.spring.example.controllers.CacheController.get*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable
    {
        //Advice
        long startTimeinMillis=System.currentTimeMillis();
        Object result = joinPoint.proceed();
        logger.info("time taken for key:{} is:{} ms", Arrays.toString(joinPoint.getArgs()), (System.currentTimeMillis() - startTimeinMillis));
        return result;
    }
}
