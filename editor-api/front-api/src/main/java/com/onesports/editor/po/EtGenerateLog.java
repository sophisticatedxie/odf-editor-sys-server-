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
 * 生成日志表
 * </p>
 *
 * @author xiejiarong
 * @since 2020-07-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel("生成日志表")
public class EtGenerateLog extends BaseEntity {

private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "生成日志id")
    @TableId(value = "log_id", type = IdType.UUID)
    private String logId;

    @ApiModelProperty(value = "模板ID")
    private String templateId;

    @ApiModelProperty(value = "数据源")
    private Blob sourceData;

    @ApiModelProperty(value = "ip地址")
    private String ip;

    @ApiModelProperty(value = "浏览器信息")
    private String explorerInfo;

    @ApiModelProperty(value = "生成结果(xml)")
    private Blob result;




}
