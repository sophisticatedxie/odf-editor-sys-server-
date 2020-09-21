package com.onesports.editor.web;


import com.onesports.editor.annotations.SecurityApi;
import com.onesports.editor.entity.model.BaseController;
import com.onesports.editor.entity.vo.ResultVO;
import com.onesports.editor.po.EtTemplate;
import com.onesports.editor.service.EtTemplateService;
import com.onesports.editor.vo.TemplateVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

/**
 * <p>标题: 模板表控制层</p>
 * <p>描述: </p>
 * <p>版权: Copyright (c) 2020</p>
 *
 * @version: 1.0
 * @author: xiejiarong
 * @date 2020-07-22
 */
@RestController
@RequestMapping("/et_center/editor-api/etTemplate")
@Api(tags = "模板表控制层")
public class EtTemplateController extends BaseController<EtTemplateService, EtTemplate> {

    @GetMapping("/initAll")
    @SecurityApi
    @ApiOperation("模板平台初始化节点和属性")
    public ResultVO initAll() throws Exception {
        return ResultVO.data(this.service.initAll());
    }

    @GetMapping("/getTemplateById")
    @ApiOperation("根据模板ID获取模板对象")
    public ResultVO getTemplateById(String id){
        EtTemplate etTemplate=this.service.getById(id);
        return ResultVO.data(etTemplate.converToDto());
    }

    @PostMapping("/saveOrUpdate")
    @ApiOperation("保存或更新单个模板对象")
    @CacheEvict(value = "templateList",allEntries = true)
    public ResultVO saveOrUpdate(@RequestBody TemplateVO templateVO){
        EtTemplate template=templateVO.converToEntity();
        this.service.saveTemplate(template);
        return ResultVO.success("保存成功");
    }


    @PostMapping("/genXmlResult/{templateId}")
    @ApiOperation("在线生成xml结果")
    public ResultVO genXmlResult(@PathVariable("templateId") String templateId){
        return ResultVO.data(this.service.genXmlResult(templateId));
    }

//    public static void main(String[] args) throws IOException {
//        Document document=DocumentHelper.createDocument();
//        document.setRootElement(document.addElement("OdfBody"));
//        Element roor=document.getRootElement();
//        roor.addElement("foreach");
//        roor.element("foreach").addAttribute("v-for","session in sessions");
//        roor.element("foreach").addAttribute("value","var sessionNames = session.sessionName;");
//
//        roor.element("foreach").addElement("Competition");
//        //创建字符串缓冲区
//        StringWriter stringWriter = new StringWriter();
//        //设置文件编码
//        OutputFormat xmlFormat = new OutputFormat();
//        xmlFormat.setEncoding("UTF-8");
//        // 设置换行
//        xmlFormat.setNewlines(true);
//        // 生成缩进
//        xmlFormat.setIndent(true);
//        // 使用4个空格进行缩进, 可以兼容文本编辑器
//        xmlFormat.setIndent("    ");
//
//        //创建写文件方法
//        XMLWriter xmlWriter = new XMLWriter(stringWriter,xmlFormat);
//        //写入文件
//        xmlWriter.write(document);
//        //关闭
//        xmlWriter.close();
//        // 输出xml
//        String result=stringWriter.toString();
//        Pattern pattern=Pattern.compile ("<foreach");
//        Matcher matcher=pattern.matcher(result);
//        while (matcher.find()){
//            System.out.println(matcher.group());
//        }
//
//
//    }






}

