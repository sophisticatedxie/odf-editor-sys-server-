package com.onesports.editor.formatter;

import cn.hutool.core.date.DateUtil;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

/**
 * @program: odf-editor-system
 * @description: 自定义日期转换器
 * @author: xjr
 * @create: 2020-07-22 17:22
 **/

public class CustomDateFormatter implements Formatter<Date> {
    @Override
    public Date parse(String s, Locale locale) throws ParseException {
        if ("".equals(s) || "null".equals(s)) {
            return null;
        }
        return DateUtil.parse(s,"yyyy-MM-dd HH:mm:ss");
    }

    @Override
    public String print(Date date, Locale locale) {
        return DateUtil.format(date,"yyyy-MM-dd HH:mm:ss");
    }
}
