package com.onesports.editor.utils;

import lombok.extern.log4j.Log4j2;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.StringTemplateResourceLoader;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Map;

@Log4j2
@Component
public class BeetlUtils {

    private static StringTemplateResourceLoader resourceLoader;

    private static Configuration cfg;

    private static GroupTemplate gt;

    static {
        try {
            resourceLoader = new StringTemplateResourceLoader();
            cfg = Configuration.defaultConfiguration();
            gt = new GroupTemplate(resourceLoader, cfg);
            gt.registerFormat("BtDateFormat",new BtDateFormat());
        } catch (IOException e) {
            log.error("beetl模版引擎初始化异常", e.getMessage(), e);
        }
    }


    /**
     * odf生成本地文件
     * @param extParam 数据map
     * @param templateContent 模板文件字符串
     * @param targetPath 输出指定文件位置
     * @param fileName 输出文件名称
     */
    public static void rendToWriter(Map<String, Object> extParam, String templateContent, String targetPath, String fileName) {
        Template st = initTemplate(templateContent, extParam);
        Writer writer = null;
        try {
            File path = new File(targetPath);
            if (!path.exists()) {
                path.mkdirs();
            }
            writer = new FileWriter(targetPath + File.separator  + fileName);
        } catch (IOException e) {
            log.error("beetl导出xml文件异常", e.getMessage(), e);
        }
        st.renderTo(writer);
    }

    /**
     * 生成流（用于前端流下载）
     */
    public static InputStream rendToInputStream(Map<String, Object> extParam, String templatePath) {
        Template st =initTemplate(templatePath,extParam);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        st.renderTo(os);
        InputStream is = new ByteArrayInputStream(os.toByteArray());
        return is;
    }

    /**
     *
     * @description 填充模板，输出odf的xml字符串
     * @param extParam:odf填充数据
     * @param templateContent 模板内容
     * @return xml字符串
     * @author xiejiarong
     * @date 2020年08月05日 15:54
     */
    public static String render(Map<String, Object> extParam,String templateContent){
        Template template=initTemplate(templateContent, extParam);
        return template.render();
    }

    /**
     *
     * @description 初始化模板，参数绑定
     * @param templateContent:模板文件字符串
     * @param params  传入初始变量
     * @return 模板对象
     * @author xiejiarong
     * @date 2020年08月05日 15:57
     */
    private static Template initTemplate(String templateContent, Map<String,Object> params ){
        Template template=gt.getTemplate(templateContent);
        template.binding("root",params);
        return template;
    }


}
