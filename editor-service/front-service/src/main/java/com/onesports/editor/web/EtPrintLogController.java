package com.onesports.editor.web;


import com.onesports.editor.entity.model.BaseController;
import com.onesports.editor.po.EtPrintLog;
import com.onesports.editor.service.EtPrintLogService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>标题: 打印日志表控制层</p>
 * <p>描述: </p>
 * <p>版权: Copyright (c) 2020</p>
 *
 * @version: 1.0
 * @author: xiejiarong
 * @date 2020-07-22
 */
@RestController
@RequestMapping("/et_center/editor-api/etPrintLog")
@Api(tags = "打印日志表控制层")
public class EtPrintLogController extends BaseController<EtPrintLogService, EtPrintLog> {




}

