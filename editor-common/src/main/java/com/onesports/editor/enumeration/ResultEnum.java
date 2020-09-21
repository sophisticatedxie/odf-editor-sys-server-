package com.onesports.editor.enumeration;

import com.onesports.editor.constant.HttpStatusConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @program: odf-editor-system
 * @description: 相应结果状态
 * @author: xjr
 * @create: 2020-07-21 10:54
 **/
@Getter
@AllArgsConstructor
public enum ResultEnum {

    SUCCESS(HttpStatusConstant.SUCCESS,"调用成功"),

    ERROR(HttpStatusConstant.ERROR,"业务异常"),

    NO_AUTH(HttpStatusConstant.NO_AUTH,"未授权");



    private String code;

    private String msg;

    public static ResultEnum getInstance(String code){
       for(ResultEnum resultEnum:ResultEnum.values()){
           if (resultEnum.getCode().equals(code)){
               return resultEnum;
           }
       }
       return null;
    }
}
