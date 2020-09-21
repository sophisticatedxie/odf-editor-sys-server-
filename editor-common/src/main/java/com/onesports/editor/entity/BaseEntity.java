package com.onesports.editor.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: odf-editor-system
 * @description: 基类
 * @author: xjr
 * @create: 2020-07-20 18:07
 **/
@Data
@NoArgsConstructor
public class BaseEntity implements Serializable {

    @ApiModelProperty(value = "创建者")
    @TableField(value = "create_by",fill = FieldFill.INSERT)
    protected String createAuthor;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date createTime;

    @ApiModelProperty(value = "更新者")
    protected String modifiedBy;

    @ApiModelProperty(value = "更新时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date modifiedTime;
}
