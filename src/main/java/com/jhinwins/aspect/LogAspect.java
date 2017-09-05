package com.jhinwins.aspect;

import com.jhinwins.domain.RedisConnCfg;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Jhinwins on 2017/9/4  9:23.
 * Desc:用于输出日志
 */
@Aspect
@Component
public class LogAspect {

    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    /**
     * 用于计算方法执行时间
     */
    @Around("execution(* com.jhinwins.controller.RedisController.*(..))")
    public Object calcExecTime(ProceedingJoinPoint pjp) throws Throwable {
        long preT = System.currentTimeMillis();
        logger.info("执行控制器：" + pjp.getSignature().getDeclaringTypeName() + ":" + pjp.getSignature().getName());
        logger.info("参数列表：" + pjp.getArgs());
        Object proceed = pjp.proceed();
        logger.info("方法执行时长：" + (System.currentTimeMillis() - preT) + " ms");
        return proceed;
    }
}
