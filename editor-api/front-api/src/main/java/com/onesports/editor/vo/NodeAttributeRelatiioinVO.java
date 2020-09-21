package com.onesports.editor.vo;

import com.onesports.editor.po.EtAttribute;
import com.onesports.editor.po.EtNode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @program: odf-editor-system
 * @description: 节点属性关联视图对象
 * @author: xjr
 * @create: 2020-08-18 09:46
 **/
@Data
public class NodeAttributeRelatiioinVO  implements Serializable {

    @ApiModelProperty("节点id")
    private String id;

    @ApiModelProperty("节点")
    private EtNode node;

    @ApiModelProperty("属性列表")
    private List<EtAttribute> attrs;

}
