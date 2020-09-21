package com.onesports.editor.vo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @program: odf-editor-system
 * @description: 模板组件视图对象
 * @author: xjr
 * @create: 2020-07-27 16:40
 **/
@Getter
@Setter
@NoArgsConstructor
public class TemplateComponentsVo implements Serializable {

    private List<NodeAttributeRelatiioinVO> nodes;

    private List<TemplatesVo> templates;

    @Override
    public String toString() {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("nodes",JSON.toJSONString(nodes,SerializerFeature.WriteMapNullValue));
        jsonObject.put("templates",JSON.toJSONString(templates,SerializerFeature.WriteMapNullValue));
        return JSON.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue);
    }
}
