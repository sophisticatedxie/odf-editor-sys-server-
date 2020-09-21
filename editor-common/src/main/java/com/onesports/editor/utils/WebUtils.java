package com.onesports.editor.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * @program: odf-editor-system
 * @description: web工具类
 * @author: xjr
 * @create: 2020-07-21 14:15
 **/

public final class WebUtils {

    public static ServletRequest getCurrentRequest(){
        ServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }

    public static ServletResponse getCurrentResponse(){
        ServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        return response;
    }

    public static HttpSession getCurrentSession(){
        return ((HttpServletRequest)getCurrentRequest()).getSession();
    }

    public static Object getSessionAttribute(String key){
        return Optional.ofNullable(getCurrentSession().getAttribute(key)).orElse(Optional.empty());
    }

    public static String getRequestHeader(String key){
        return Optional.ofNullable(((HttpServletRequest)getCurrentRequest()).getHeader(key)).orElse(Optional.empty().toString());
    }
}
