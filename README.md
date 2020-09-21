#  odf-editor-sys-server

**odf模板制作平台服务端工程**


odf模板平台是由一体体育内部自行研发的可视化制作ODF(Open Document Format)模板的工具，业务模块划分，尽量贴合互联网公司的架构体系。所以，除了业务本身的复杂度不是很高之外，整体的架构基本和实际架构相差无几，项目采用前后端分离模式开发，本工程为后端项目，前端工程地址详见[odf-editor-sys-ui](http://gitlab.onesport.com.cn/competition/odf-editor-sys/odf-editor-sys-ui "odf-editor-sys-ui").

![Pandao editor.md](https://images.gitee.com/uploads/images/2020/0917/111316_174e752c_2291825.jpeg "Pandao editor.md")

## 一、开发前阅读

#### 1.框架技术选型
>springboot 2.3.1.RELEASE

>构建工具选择gradle 6.6

>数据库使用H2内嵌数据库

>后台api文档使用自定义knife4j

>持久层框架选择mybatis-plus 3.3.1

------------
#### 2.多环境配置
本地开发默认使用application-dev.yml(主要自定义数据库文件存放位置)

------------


#### 3.工程目录说明

```java
 |-odf-editor-sys-server                    
      |-editor-api             数据层
	    |backend-api      后台数据层
		|front-api        前台数据层
      |-editor-common          公共模块
	    |-annotations     通用注解
		|-aspects         切面类
		|-config          全局配置
		|-constant        通用常量类
		|-context         上下文
		|-entity          通用实体
		|-enumeration     通用枚举
		|-exception       自定义异常
		|-filter          过滤器
		|-formatter       转换器
		|-handler         异常处理
		|-init            自定义初始化策略
		|-utils           通用工具类
	  |-editor-service         web模块(rest接口)
	    |-backend-service       后台web
		|-front-service         前台web
```


#### 4.本地开发步骤

> 1、本地安装gradle(或者直接使用idea内置的gradle wrapper自动下载),下载完毕后配置系统环境变量GRADLE_USER_HOME->本地仓库路径。

  > 2、导入项目根目录build.gradle完成项目依赖下载

  > 3、项目目录下的et_center.mv.db和et_center.trace.db自行选择本地磁盘存放，在对应的web层resources文件夹下自建application-dev.yml，配置相应的h2持久文件路径
  ```java
spring:
  datasource:
    #h2文件数据库 内存模式连接配置 库名:et_center
    url: jdbc:h2:file:你的本地存放路径/et_center
```

  > 运行前台工程front-service->FrontServiceApplication,启动完毕访问http://localhost:8081/doc.html 后台api文档地址。
