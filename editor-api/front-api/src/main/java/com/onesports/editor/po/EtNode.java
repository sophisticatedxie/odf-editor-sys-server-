package com.onesports.editor.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.onesports.editor.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 节点表
 * </p>
 *
 * @author xiejiarong
 * @since 2020-07-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel("节点表")
public class EtNode extends BaseEntity {

private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "节点id")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "节点名字")
    private String elementName;

    @ApiModelProperty(value = "是否单节点(是否循环) 0 单节点 1多节点",example = "0")
    private Integer isSimple;

    @ApiModelProperty(value = "数据自动域名")
    private String autoField;

    @ApiModelProperty(value = "节点内容表达式")
    private String contentExpress;

    @ApiModelProperty(value = "是否是body")
    @TableField(value = "isBody")
    private String isBody;

    @ApiModelProperty("条件表达式")
    private String condition;

    @ApiModelProperty(value = "是否启动条件展示 0 否 1是",example = "0")
    private Integer enableCondition;








}
