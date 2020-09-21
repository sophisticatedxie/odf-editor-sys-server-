package com.onesports.editor;

import com.gitee.sophis.annotations.EnableApiDocument;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @program: odf-editor-system
 * @description:
 * @author: xjr
 * @create: 2020-07-22 09:39
 **/
@MapperScan("com.onesports.editor.dao")
@EnableCaching
@SpringBootApplication
@EnableApiDocument(title = "odf编辑器平台",description = "前台服务接口在线文档",groupName = "front",version = "1.0")
public class FrontServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(FrontServiceApplication.class,args);
    }


}
