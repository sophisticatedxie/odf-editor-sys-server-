package com.onesports.editor.utils;

import cn.hutool.core.io.resource.ClassPathResource;
import com.google.common.base.Charsets;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: odf-editor-system
 * @description: sql转换
 * @author: xjr
 * @create: 2020-07-22 10:07
 **/

public final class SqlConverter {
    /**
     *
     * @description 方法描述
     * @param filePath: 文件位置
     * @return  H2 sql脚本
     * @author xiejiarong
     * @date 2020年07月22日 10:15
     */
    public static String mysql2h2(String filePath) throws IOException {
        ClassPathResource classPathResource=new ClassPathResource(filePath);
        File file = classPathResource.getFile();
        String content = Files.toString(file, Charsets.UTF_8);
        content = "SET MODE MYSQL;\n\n" + content;
        content = content.replaceAll("`", "");
        content = content.replaceAll("COLLATE.*(?=D)", "");
        content = content.replaceAll("COMMENT.*'(?=,)", "");
        content = content.replaceAll("\\).*ENGINE.*(?=;)", ")");
        content = content.replaceAll("DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP", " AS CURRENT_TIMESTAMP");

        content = uniqueKey(content);
        return content;
    }



    /**
     * h2的索引名必须全局唯一
     *
     * @param content sql建表脚本
     * @return 替换索引名为全局唯一
     */
    private static String uniqueKey(String content) {
        int inc = 0;
        Pattern pattern = Pattern.compile("(?<=KEY )(.*?)(?= \\()");
        Matcher matcher = pattern.matcher(content);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group() + inc++);
        }
        matcher.appendTail(sb);
        content = sb.toString();
        return content;
    }

}
