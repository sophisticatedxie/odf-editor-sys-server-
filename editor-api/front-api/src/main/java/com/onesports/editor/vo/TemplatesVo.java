package com.onesports.editor.vo;

import com.onesports.editor.po.EtTemplateClass;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @program: odf-editor-system
 * @description: 模板视图对象
 * @author: xjr
 * @create: 2020-08-03 10:25
 **/
@Data
@Accessors(chain = true)
public class TemplatesVo implements Serializable {

    private EtTemplateClass templateClass;

    private List<TemplateVO> list;

}
