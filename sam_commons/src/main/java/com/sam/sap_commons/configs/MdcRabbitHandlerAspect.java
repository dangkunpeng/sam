package com.sam.sap_commons.configs;

import com.sam.sap_commons.utils.AjaxUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class MdcRabbitHandlerAspect {

    // 拦截所有带有 @RabbitHandler 注解的方法
    @Around("@annotation(rabbitHandler)")
    public Object aroundRabbitHandler(ProceedingJoinPoint joinPoint, RabbitHandler rabbitHandler) throws Throwable {
        // 切面前置逻辑
//        log.info("RabbitHandler 方法被调用: {}", joinPoint.getSignature());
        // 执行业务方法
        Object result = joinPoint.proceed();
        // 切面后置逻辑
//        log.info("RabbitHandler 方法执行完毕");
        AjaxUtils.removeTraceId();
        return result;
    }
}