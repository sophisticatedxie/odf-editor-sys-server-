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
 * 加密算法表
 * </p>
 *
 * @author xiejiarong
 * @since 2020-07-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel("加密算法表")
public class EtCrypto extends BaseEntity {

private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "加密算法id")
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    @ApiModelProperty(value = "算法内容(js函数)")
    private Blob funcContent;

    @ApiModelProperty(value = "算法名字")
    private String funcName;

    @ApiModelProperty(value = "关联id")
    private String relationId;




}
