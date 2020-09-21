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
 * <p>
 * 模板节点表
 * </p>
 *
 * @author xiejiarong
 * @since 2020-07-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel("模板属性表")
public class EtAttribute extends BaseEntity {

private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "属性id")
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    @ApiModelProperty(value = "属性名")
    private String attributeName;

    @ApiModelProperty(value = "属性内容占位符表达式")
    private String valExpress;




}
