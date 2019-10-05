package com.ahsp.aspect;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author：Dr.chen
 * @date：2019/8/31 14:28
 * @Description：对数据的操作结果记录
 */
@Component
@Aspect
public class ResultLoggerAspect {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(public * com.ahsp.service.ResultServiceImpl.*(..))")
    public void resultJoinPointExpression() {
    }

    @Pointcut("execution(public * com.ahsp.service.ExpertServiceImpl.*(..))")
    public void expertJoinPointExpression() {
    }

    @AfterThrowing(value = "resultJoinPointExpression()", throwing = "e")
    public void logResultError(Exception e) {
        logger.error(e.getMessage());
    }

    @AfterThrowing(value = "expertJoinPointExpression()", throwing = "e")
    public void logExpertError(Exception e) {
        logger.error(e.getMessage());
    }
}
