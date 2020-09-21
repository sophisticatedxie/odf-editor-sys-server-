package com.onesports.editor.filter;

import cn.hutool.core.lang.UUID;
import com.onesports.editor.context.EtContextHolder;
import com.onesports.editor.entity.vo.ResultVO;
import com.onesports.editor.enumeration.ResultEnum;
import com.onesports.editor.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * @program: odf-editor-system
 * @description: 过滤器
 * @author: xjr
 * @create: 2020-07-21 11:04
 **/
@WebFilter
@Component
@Slf4j
public class EtFilter implements Filter {
    private static final String _JSON_CONTENT = "application/json; charset=UTF-8";
    private static final String _HTML_CONTENT = "text/html; charset=UTF-8";
    private static final String _403_JSON = "{'code': '403', 'msg': '访问被拒绝，客户端未授权！'}";
    private static final String _403_HTML = "<html><body><div style='text-align:center'><h1 style='margin-top: 10px;'>403 Forbidden!</h1><hr><span>@lichmama</span></div></body></html>";



    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        EtContextHolder.set(EtContextHolder.REQUEST_ID, UUID.randomUUID().toString());
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response= (HttpServletResponse) servletResponse;
        allowCros(response);
        boolean ajax=isAjaxRequest(request);
        BiPredicate<HttpServletRequest,Predicate<HttpServletRequest>> func=this::isSecurityRequest;
        if ("options".equalsIgnoreCase(request.getMethod())){
            //复杂跨域请求,放行预检
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }
        //放行swagger
        if (func.test(request,this::allowSwagger)){
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }
        if (func.test(request,rq->"ovr-common".equals(rq.getHeader("reference")))){
            filterChain.doFilter(servletRequest,servletResponse);
            log.info("来自通用场馆rest调用，url:{}",request.getRequestURI());
            return;
        }
        log.info("operator：{},url:{},referer:{}",request.getHeader("operator"),request.getRequestURL(),request.getHeader("referer"));
        //判断token，token为空直接返回无权限
        if (func.test(request,r->r.getHeader(EtContextHolder.TOKEN)!=null)){
            String token=request.getHeader(EtContextHolder.TOKEN);
            String sessionId= Optional.ofNullable(request.getSession().getAttribute(EtContextHolder.OPERATOR)).toString();
            //token 未过期，即有效
            if (JwtUtil.verity(token)){
                try {
                    EtContextHolder.set(EtContextHolder.OPERATOR,JwtUtil.decode(token));
                    EtContextHolder.set(EtContextHolder.TOKEN,token);
                } catch (Exception e) {
                    response.setStatus(Integer.parseInt(ResultEnum.SUCCESS.getCode()));
                    response.getWriter().println(ajax?ResultVO.failure("403",e.getMessage()):_403_HTML);
                }
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
            //token 过期,判断session
            if ("Optional.empty".equals(sessionId)){
                response.setStatus(Integer.parseInt(ResultEnum.SUCCESS.getCode()));
                response.getWriter().println(ajax? ResultVO.failure("403","用户已过期，请重新登录"):_403_HTML);
                EtContextHolder.clear();
                return;
            }else{//不为空就刷新token
                EtContextHolder.set(EtContextHolder.TOKEN, JwtUtil.sign(sessionId));
                EtContextHolder.set(EtContextHolder.OPERATOR,sessionId);
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }else{
            EtContextHolder.clear();
            response.setContentType(ajax?_JSON_CONTENT:_HTML_CONTENT);
            response.setStatus(Integer.parseInt(ResultEnum.SUCCESS.getCode()));
            response.getWriter().println(ajax?ResultVO.failure("403","没有权限访问"):_403_HTML);
        }
    }

    @Override
    public void destroy() {

    }


    /**
     *
     * 校验安全请求
     * @param request: 请求
     * @param check 断言
     * @return  T
     * @author xiejiarong
     * @date 2020年07月21日 11:13
     */
    private  <T> boolean  isSecurityRequest(T request, Predicate<? super T> check){
        return check.test(request);
    }


    /**
     * 判断请求是否是AJAX请求
     * @param request 请求
     * @return boolean
     */
    private boolean isAjaxRequest(HttpServletRequest request) {
        String header = request.getHeader("X-Requested-With");
        if (header != null && header.length() > 0) {
            if ("XMLHttpRequest".equalsIgnoreCase(header)){
                return true;}
        }
        return false;
    }

    /**
     *
     * @description 方法描述  解决跨域问题
     * @param response:
     * @author xiejiarong
     * @date 2020年04月23日 10:18
     */
    public void allowCros(HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Access-Control-Allow-Credentials","true");
        response.setHeader("Access-Control-Allow-Methods","GET, HEAD, POST, PUT, DELETE, OPTIONS, PATCH");
        response.setHeader("Access-Control-Allow-Headers","authorization, content-type,token,operator");
        response.setHeader("Access-Control-Expose-Headers","X-forwared-port, X-forwarded-host");
        response.setHeader("Vary","Origin,Access-Control-Request-Method,Access-Control-Request-Headers");
        response.setHeader("Access-Control-Max-Age","60");
        response.setCharacterEncoding("UTF-8");
    }

    /**
     *
     * @description 放行swagger相关
     * @param request: 请求
     * @return boolean true 放行 false 不放
     * @author xiejiarong
     * @date 2020年07月21日 11:36
     */
    public boolean  allowSwagger(HttpServletRequest request){
        Pattern pattern= Pattern.compile("(js|css|png|html)$ | swagger|doc|webjars|v2|swagger-resources|login|socket|h2-console|druid");
        return pattern.matcher(request.getRequestURI()).find();
    }


}
