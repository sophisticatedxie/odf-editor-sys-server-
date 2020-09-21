package com.onesports.editor.aspects;

import com.onesports.editor.entity.vo.ResultVO;
import com.onesports.editor.utils.CustomCryptoUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @program: odf-editor-system
 * @description: 接口返回值加密代理
 * @author: xjr
 * @create: 2020-07-29 15:20
 **/
@Aspect
@Component
@Slf4j
public class SecurityAspect {

    @PostConstruct
    public void init(){
        log.info("SecurityAspect has been initialized,class is:{}",this.getClass().getName());
    }

    @Pointcut("@annotation(com.onesports.editor.annotations.SecurityApi)")
    public void pointCut(){

    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        ResultVO result= (ResultVO) pjp.proceed();
        result.setData(CustomCryptoUtil.encrypt(result.getData().toString()));
        return result;
    }
}
