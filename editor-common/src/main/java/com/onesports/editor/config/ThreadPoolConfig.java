package com.onesports.editor.config;

import com.onesports.editor.constant.ThreadConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @program: odf-editor-system
 * @description: 线程池配置
 * @author: xjr
 * @create: 2020-07-21 09:40
 **/
@Configuration(ThreadConstant.CONFIG_NAME)
@EnableAsync
public class ThreadPoolConfig {

    @Bean("ET-THREAD-POOL")
    public ThreadPoolTaskExecutor poo1(){
        ThreadPoolTaskExecutor threadPoolTaskExecutor=new ThreadPoolTaskExecutor();
        //设置核心线程池数目
        threadPoolTaskExecutor.setCorePoolSize(5);
        //设置空闲线程回收时间
        threadPoolTaskExecutor.setKeepAliveSeconds(60);
        //设置核心线程可被回收
        threadPoolTaskExecutor.setAllowCoreThreadTimeOut(true);
        //设置线程池最大容量
        threadPoolTaskExecutor.setMaxPoolSize(10);
        //设置等待队列长度
        threadPoolTaskExecutor.setQueueCapacity(10);
        //设置线程池前缀名
        threadPoolTaskExecutor.setThreadNamePrefix(ThreadConstant.THREAD_PREFIX);
        //当线程池和队列已满的情况下，如何处理新的任务
        //CallerRunsPolicy 由调用者自身所在的线程执行
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //初始化线程池
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }

}
