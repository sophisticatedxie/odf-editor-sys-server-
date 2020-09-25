package com.onesports.editor.po;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.onesports.editor.entity.BaseEntity;
import com.onesports.editor.vo.TemplateVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * <p>
 * 模板表
 * </p>
 *
 * @author xiejiarong
 * @since 2020-07-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel("模板表")
public class EtTemplate extends BaseEntity {

private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "模板ID")
    @TableId(value = "template_id", type = IdType.UUID)
    private String templateId;

    @ApiModelProperty(value = "模板名字")
    private String templateName;

    @ApiModelProperty(value = "所属类别")
    private String templateClassCode;

    @ApiModelProperty(value = "模板的dom加密密文")
    private byte[] templateContent;

    @ApiModelProperty(value = "模板数据来源url")
    private String restfulUrl;

    @ApiModelProperty(value = "模板对应的xml信息")
    private byte[] odfXml;

    @ApiModelProperty(value = "使用加密算法ID")
    private String cryptoId;

    @ApiModelProperty("odfbody节点id")
    private String bodyId;

    public TemplateDto converToDto(){
        TemplateDto templateDto=new TemplateDto();
        templateDto.setTemplateId(this.getTemplateId());
        templateDto.setTemplateName(this.getTemplateName());
        templateDto.setTemplateContent(this.getOdfXml()==null?null:new String(this.getOdfXml(), StandardCharsets.UTF_8));
        templateDto.setCreateBy(this.getCreateAuthor());
        return templateDto;
    }

    public TemplateVO converToVo(){
        TemplateVO templateVO=new TemplateVO();
        BeanUtils.copyProperties(this,templateVO);
        Optional.ofNullable(this.getOdfXml()).ifPresent(xml->templateVO.setOdfXmlStr(new String(xml, StandardCharsets.UTF_8)));
        Optional.ofNullable(this.getTemplateContent()).ifPresent(content->templateVO.setTemplateContentStr(new String(content, StandardCharsets.UTF_8)));
        return templateVO;
    }

    @Setter
    @Getter
    private static class TemplateDto{

        private String templateId;

        private String templateContent;

        private String templateName;

        private String createBy;

        @Override
        public String toString() {
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("templateId", templateId);
            jsonObject.put("templateContent",templateContent);
            jsonObject.put("templateName",templateName);
            jsonObject.put("createBy",createBy);
            return JSON.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue);
        }

    }
}
