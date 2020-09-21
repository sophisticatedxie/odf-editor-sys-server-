package com.onesports.editor.entity.vo;

import com.onesports.editor.context.EtContextHolder;
import com.onesports.editor.enumeration.ResultEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @program: odf-editor-system
 * @description: 响应结果视图对象
 * @author: xjr
 * @create: 2020-07-21 11:41
 **/
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("返回数据")
public class ResultVO<T> implements Serializable {

    @ApiModelProperty("返回结果状态编码")
    private String code;
    @ApiModelProperty("错误信息")
    private String error;
    @ApiModelProperty("返回消息")
    private String message;
    @ApiModelProperty("返回数据")
    private T data;
    @ApiModelProperty("客户端令牌")
    private String token;

    public static <T> ResultVO<T> success(String message) {
        return ResultVO.<T>builder().code(ResultEnum.SUCCESS.getCode()).message(message).token(tokenGet()).build();
    }

    public static <T> ResultVOBuilder<T> success() {
        return ResultVO.<T>builder().code(ResultEnum.SUCCESS.getCode());
    }

    public static <T> ResultVO<T> failure(String message) {
        return ResultVO.<T>builder().code(ResultEnum.ERROR.getCode()).error(message).token(tokenGet()).build();
    }

    public static <T> ResultVO<T> failure(String code, String message) {
        return ResultVO.<T>builder().code(code).error(message).token(tokenGet()).build();
    }

    public static <T> ResultVOBuilder<T> failure() {
        return ResultVO.<T>builder().code(ResultEnum.ERROR.getCode());
    }

    public static <T> ResultVO<T> data(T data) {
        return ResultVO.<T>success().data(data).token(tokenGet()).build();
    }
    public static String tokenGet(){
        return EtContextHolder.get(EtContextHolder.TOKEN);
    }


}
