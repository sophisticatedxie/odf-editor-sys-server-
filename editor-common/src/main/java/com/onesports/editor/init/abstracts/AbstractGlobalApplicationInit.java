package com.onesports.editor.init.abstracts;

import com.onesports.editor.init.GlobalApplicationInit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

/**
 * @program: socket-actuator-spring-boot-starter
 * @description: 全局初始化工作抽象
 * @author: xjr
 * @create: 2020-06-24 16:46
 **/
@Slf4j
public abstract class AbstractGlobalApplicationInit implements GlobalApplicationInit, ApplicationContextAware, EnvironmentAware, BeanNameAware, InitializingBean {

    private ApplicationContext applicationContext;

    private Environment environment;

    private String beanName;

    @Override
    public final void init(){
        log.info("No {} start Initializing-------------------------",this.order());
        try {
            execute(applicationContext,environment,beanName);
        }catch (Throwable t){

        }
        log.info("No {} finishing Task------------------------------",this.order());
    }

    /**
     *
     * @description 实际需要实现的业务初始化逻辑
     * @param applicationContext: spring上下文
     * @param environment:环境变量
     * @param beanName :正在执行任务的bean名字
     * @throws Throwable
     * @author xiejiarong
     * @date 2020年06月24日 16:52
     */
    public abstract void execute(ApplicationContext applicationContext,Environment environment,String beanName) throws Throwable ;


    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("init class:{} has finished the init work of bean.",this.getClass().getName());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
}
