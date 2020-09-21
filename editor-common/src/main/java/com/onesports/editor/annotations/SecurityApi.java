package com.onesports.editor.annotations;

import java.lang.annotation.*;

/**
 * @program: odf-editor-system
 * @description: 接口返回值加密注解
 * @author: xjr
 * @create: 2020-07-29 15:17
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface SecurityApi {
    boolean require() default  true;
}
