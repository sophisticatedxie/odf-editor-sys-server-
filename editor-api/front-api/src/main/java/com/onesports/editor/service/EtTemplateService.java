package com.onesports.editor.service;


import com.onesports.editor.entity.model.BaseService;
import com.onesports.editor.exception.EtException;
import com.onesports.editor.po.EtTemplate;
import com.onesports.editor.vo.TemplateComponentsVo;

/**
 * <p>标题: 模板表 Service 接口</p>
 * <p>描述: </p>
 * <p>版权: Copyright (c) 2020</p>
 *
 * @version: 1.0
 * @author: xiejiarong
 * @date 2020-07-22
 */
public interface EtTemplateService extends BaseService<EtTemplate> {

    TemplateComponentsVo initAll() throws Exception;

    EtTemplate saveTemplate(EtTemplate etTemplate) throws EtException;

    String genXmlResult(String templateId) throws EtException;
}
