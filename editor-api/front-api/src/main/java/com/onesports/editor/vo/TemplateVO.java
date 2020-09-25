package com.onesports.editor.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.onesports.editor.po.EtTemplate;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * @program: odf-editor-system
 * @description: 模板视图对象
 * @author: xjr
 * @create: 2020-08-07 16:56
 **/
@Data
public class TemplateVO implements Serializable {

    @ApiModelProperty(value = "模板ID")
    @TableId(value = "template_id", type = IdType.UUID)
    private String templateId;

    @ApiModelProperty(value = "模板名字")
    private String templateName;

    @ApiModelProperty(value = "所属类别")
    private String templateClassCode;

    @ApiModelProperty(value = "模板的dom加密密文")
    private String templateContentStr;

    @ApiModelProperty(value = "模板数据来源url")
    private String restfulUrl;

    @ApiModelProperty(value = "模板对应的xml信息")
    private String odfXmlStr;

    @ApiModelProperty(value = "使用加密算法ID")
    private String cryptoId;


    @ApiModelProperty("odfbody节点id")
    private String bodyId;

    public EtTemplate converToEntity(){
        EtTemplate entity=new EtTemplate();
        BeanUtils.copyProperties(this,entity);
        Optional.ofNullable(this.getOdfXmlStr()).ifPresent(xml->entity.setOdfXml(xml.getBytes(StandardCharsets.UTF_8)));
        Optional.ofNullable(this.getTemplateContentStr()).ifPresent(content->entity.setTemplateContent(content.getBytes(StandardCharsets.UTF_8)));
        return entity;
    }
}
