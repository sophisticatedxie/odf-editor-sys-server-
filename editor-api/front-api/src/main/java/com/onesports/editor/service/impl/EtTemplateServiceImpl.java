package com.onesports.editor.service.impl;


import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.onesports.editor.dao.EtTemplateDao;
import com.onesports.editor.entity.model.BaseServiceImpl;
import com.onesports.editor.exception.EtException;
import com.onesports.editor.po.EtTemplate;
import com.onesports.editor.service.*;
import com.onesports.editor.utils.BeetlUtils;
import com.onesports.editor.vo.TemplateComponentsVo;
import com.onesports.editor.vo.TemplatesVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>标题: 模板表 Service 实现类</p>
 * <p>描述: </p>
 * <p>版权: Copyright (c) 2020</p>
 *
 * @version: 1.0
 * @author: xiejiarong
 * @date 2020-07-22
 */
@Service
public class EtTemplateServiceImpl extends BaseServiceImpl<EtTemplateDao, EtTemplate> implements EtTemplateService {

    @Autowired
    EtNodeService etNodeService;

    @Autowired
    EtAttributeService etAttributeService;

    @Autowired
    EtAttributeNodeRelationshipService relationshipService;

    @Autowired
    EtTemplateClassService classService;
    @Override
    @Cacheable("templateList")
    public TemplateComponentsVo initAll() throws Exception {
        List<TemplatesVo> templatesVos=classService.list().parallelStream()
                .map(data->{
                    TemplatesVo vo=new TemplatesVo();
                    vo.setTemplateClass(data);
                    vo.setList(list(Wrappers.<EtTemplate>lambdaQuery().eq(EtTemplate::getTemplateClassCode,data.getId())).stream().map(EtTemplate::converToVo).collect(Collectors.toList()));
                    return vo;
                }).collect(Collectors.toList());
        TemplateComponentsVo templateComponentsVo=new TemplateComponentsVo();
        templateComponentsVo.setNodes(etNodeService.nodes());
        templateComponentsVo.setTemplates(templatesVos);
        return templateComponentsVo;
    }

    @Override
    public EtTemplate saveTemplate(EtTemplate etTemplate) throws EtException {
        return insertOrUpdate(etTemplate);
    }

    @Override
    public String genXmlResult(String templateId) throws EtException {
        EtTemplate template= Optional.<EtTemplate>ofNullable(this.baseDao.selectById(templateId))
                .orElseThrow(()->new EtException("找不到模板id为"+templateId+"的模板"));
        String result= HttpUtil.get(template.getRestfulUrl());
        Map<String,Object> resultMap= (Map<String, Object>) JSON.parseObject(result).get("data");
        String odfResult= BeetlUtils.render(resultMap,new String(template.getOdfXml()));
        return odfResult;
    }




}
