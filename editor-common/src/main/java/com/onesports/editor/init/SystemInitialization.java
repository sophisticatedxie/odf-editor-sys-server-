package com.onesports.editor.init;

import cn.hutool.core.collection.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * @program: socket-actuator-spring-boot-starter
 * @description: 系统初始化执行任务
 * @author: xjr
 * @create: 2020-06-24 17:03
 **/
@Component("systemInitialization")
@Slf4j
public class SystemInitialization implements CommandLineRunner {
    private List<GlobalApplicationInit> globalApplicationInits;

    @Autowired
    @Qualifier("ET-THREAD-POOL")
    private Executor threadPoolTaskExecutor;

    public SystemInitialization(@Autowired(required = false) List<GlobalApplicationInit> globalApplicationInits){
        this.globalApplicationInits=globalApplicationInits;
    }
    @Override
    public void run(String... args) throws Exception {
        log.info("class:{},global init task starts",this.getClass().getName());
        if (CollectionUtil.isEmpty(globalApplicationInits)){
            return;
        }
        this.globalApplicationInits
                .stream()
                .sorted(Comparator.comparing(GlobalApplicationInit::order))
                .forEach(data->{
                    if (data.async()){
                        threadPoolTaskExecutor.execute(data::init);
                    }else{
                        data.init();
                    }
                });
        log.info("class:{},global init task  finish",this.getClass().getName());
    }
}
