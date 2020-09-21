package com.onesports.editor.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.onesports.editor.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.sql.Blob;

/**
 * <p>
 * 打印日志表
 * </p>
 *
 * @author xiejiarong
 * @since 2020-07-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel("打印日志表")
public class EtPrintLog extends BaseEntity {

private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "打印id")
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    @ApiModelProperty(value = "打印机名字")
    private String machine;

    @ApiModelProperty(value = "打印机IP")
    private String ip;

    @ApiModelProperty(value = "打印内容")
    private Blob content;

    @ApiModelProperty(value = "模板ID")
    private String templateId;

}
