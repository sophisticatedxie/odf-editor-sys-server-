package com.onesports.editor.web;

import com.onesports.editor.entity.model.BaseController;
import com.onesports.editor.po.EtTemplateClass;
import com.onesports.editor.service.EtTemplateClassService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: odf-editor-system
 * @description: 模板类别控制层
 * @author: xjr
 * @create: 2020-07-31 10:34
 **/
@RestController
@RequestMapping("/et_center/editor-api/etTemplateClass")
@Api(tags = "模板类别表控制层")
public class EtTemplateClassController extends BaseController<EtTemplateClassService, EtTemplateClass> {

}
