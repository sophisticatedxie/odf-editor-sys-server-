package com.onesports.editor.handler;

import com.onesports.editor.entity.vo.ResultVO;
import com.onesports.editor.exception.EtException;
import com.onesports.editor.formatter.CustomDateFormatter;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @program: odf-editor-system
 * @description: 全局异常捕获
 * @author: xjr
 * @create: 2020-07-21 15:30
 **/
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
     *
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addCustomFormatter(new CustomDateFormatter());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public Map<String, Object> methodArgNotValidException(ConstraintViolationException cve, HttpServletRequest httpServletRequest) {
        Set<ConstraintViolation<?>> cves = cve.getConstraintViolations();
        StringBuffer errorMsg = new StringBuffer();
        cves.forEach(ex -> errorMsg.append(ex.getMessage()));
        Map<String, Object> respMap = new HashMap<>(4);
        respMap.put("code", -1);
        respMap.put("error", errorMsg);
        return respMap;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({MethodArgumentNotValidException.class})
    public Map<String, Object> methodDtoNotValidException(Exception ex, HttpServletRequest request) {
        MethodArgumentNotValidException c = (MethodArgumentNotValidException) ex;
        List<ObjectError> errors = c.getBindingResult().getAllErrors();
        StringBuffer errorMsg = new StringBuffer();
        errors.stream().forEach(x -> {

            errorMsg.append(x.getDefaultMessage()).append(";");
        });
        Map<String, Object> respMap = new HashMap<>(4);
        respMap.put("code", -1);
        respMap.put("error", errorMsg);
        return respMap;
    }

    @ExceptionHandler(EtException.class)
    public ResultVO EtExceptionHandler(EtException ex){
        return ResultVO.failure(ex.getMessage());
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResultVO duplicateKeyException(DuplicateKeyException ex){
        return ResultVO.failure("数据库主键冲突,请检查请求参数");
    }

    @ExceptionHandler(Exception.class)
    public ResultVO unknownException(Exception ex){
        return ResultVO.failure("业务异常,具体原因"+ex.getMessage());
    }


}
