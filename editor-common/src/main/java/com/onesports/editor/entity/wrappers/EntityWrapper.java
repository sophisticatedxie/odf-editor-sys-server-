package com.onesports.editor.entity.wrappers;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @program: odf-editor-system
 * @description: 实体包装类
 * @author: xjr
 * @create: 2020-07-21 11:45
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("更新实体包装类")
public class EntityWrapper<T> {

    @ApiModelProperty("旧数据(更新条件)")
    @NotNull
    public T oldInfo;

    @ApiModelProperty("要更新的数据")
    @NotNull
    public T newInfo;
}
