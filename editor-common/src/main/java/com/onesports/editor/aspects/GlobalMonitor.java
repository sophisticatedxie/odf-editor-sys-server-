package com.onesports.editor.aspects;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;

/**
 * @program: odf-editor-system
 * @description: 全局监听器
 * @author: xjr
 * @create: 2020-07-28 16:22
 **/
@Aspect
@Component
@Slf4j
public class GlobalMonitor {

    private final ThreadLocal<StopWatch> timer=ThreadLocal.withInitial(StopWatch::new);

    @PostConstruct
    public void init(){
        log.info("global monitor has been initialized,clazz:{}",this.getClass().getName());
    }

    @Pointcut("execution(public * com.onesports.editor.web.*.*(..))")
    public void pointCut(){
    }

    @Before("pointCut()")
    public void before(JoinPoint joinPoint){
        Signature signature = joinPoint.getSignature();
        Method method = ((MethodSignature) signature).getMethod();
        ApiOperation apiOperation=method.getAnnotation(ApiOperation.class);
        timer.get().start(apiOperation.value());
    }

    @After("pointCut()")
    public void after(){
        timer.get().stop();
        log.info("请求:{}执行完毕,接口执行时间:{}ms",timer.get().getLastTaskName(),timer.get().getTotalTimeMillis());
        timer.remove();
    }



}
