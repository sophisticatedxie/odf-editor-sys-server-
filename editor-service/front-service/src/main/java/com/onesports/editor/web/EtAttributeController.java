package com.onesports.editor.web;


import com.onesports.editor.entity.model.BaseController;
import com.onesports.editor.po.EtAttribute;
import com.onesports.editor.service.EtAttributeService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>标题: 模板节点表控制层</p>
 * <p>描述: </p>
 * <p>版权: Copyright (c) 2020</p>
 *
 * @version: 1.0
 * @author: xiejiarong
 * @date 2020-07-22
 */
@RestController
@RequestMapping("/et_center/editor-api/etAttribute")
@Api(tags = "模板节点属性表控制层")
public class EtAttributeController extends BaseController<EtAttributeService, EtAttribute> {




}

