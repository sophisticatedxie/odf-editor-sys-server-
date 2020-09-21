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
 * 节点属性关联表
 * </p>
 *
 * @author xiejiarong
 * @since 2020-07-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel("节点属性关联表")
public class EtAttributeNodeRelationship extends BaseEntity {

private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "属性节点关联id")
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    @ApiModelProperty(value = "节点ID")
    private String nodeId;

    @ApiModelProperty(value = "属性id")
    private String attributeId;


}
