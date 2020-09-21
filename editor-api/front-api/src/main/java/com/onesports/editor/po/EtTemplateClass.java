package com.onesports.editor.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.onesports.editor.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @program: odf-editor-system
 * @description: 模板类别表
 * @author: xjr
 * @create: 2020-07-31 10:26
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel("模板类别表")
public class EtTemplateClass extends BaseEntity {

    @ApiModelProperty(value = "类别id")
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    @ApiModelProperty(value = "分类名目")
    private String className;

}
