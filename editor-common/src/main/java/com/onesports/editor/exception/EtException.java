package com.onesports.editor.exception;

import com.onesports.editor.enumeration.ResultEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: odf-editor-system
 * @description: 自定义异常
 * @author: xjr
 * @create: 2020-07-21 10:53
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class EtException extends RuntimeException{
    private ResultEnum resultEnum;

    public EtException(String message){
          super(message);
          this.resultEnum= ResultEnum.ERROR;
    }

    public EtException(ResultEnum resultEnum,String message){
        super(message);
        this.resultEnum= resultEnum;
    }



}
