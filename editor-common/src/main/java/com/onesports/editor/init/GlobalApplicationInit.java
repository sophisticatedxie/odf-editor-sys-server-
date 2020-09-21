package com.onesports.editor.init;

/**
 * @program: socket-actuator-spring-boot-starter
 * @description: 项目初始化父接口
 * @author: xjr
 * @create: 2020-06-24 16:34
 **/
public interface GlobalApplicationInit {

    /**
     *
     * @description 初始化
     * @author xiejiarong
     * @date 2020年06月24日 16:36
     */
    void init();

    /**
     *
     * @description 项目初始化顺序,越小越靠前
     * @author xiejiarong
     * @date 2020年06月24日 16:38
     */
    int order();


    /**
     *
     * @description 是否异步,若是则无视定义的order序号执行
     * @author xiejiarong
     * @date 2020年06月24日 16:44
     */
    default boolean async(){
        return false;
    }


}
