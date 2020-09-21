package com.onesports.editor.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;

import java.util.List;

/**
 *
 * @author: xiejr
 * @date 2019-06-12
 */
public class FieldUtils {

    /**
     * 驼峰法转下划线
     *
     * @param line
     *            源字符串
     * @return 转换后的字符串
     */
    public static String camel2Underline(String line) {
        return StrUtil.toUnderlineCase(line);
    }

    public static String[] camel2Underline(String[] lines) {
        if (lines == null || lines.length == 0) {
            return lines;
        }
        String[] res = new String[lines.length];
        for (int i = 0; i < lines.length; i++) {
            res[i] = StrUtil.toUnderlineCase(lines[i]);
        }
        return res;
    }

    public static String checkFields(Class<?> clazz, List<String> checkFields) {
        if (clazz == null || CollectionUtil.isEmpty(checkFields)) {
            return "字段列表为空";
        }
        TableInfo tableInfo = TableInfoHelper.getTableInfo(clazz);
        List<TableFieldInfo> fieldList = tableInfo.getFieldList();
        List<String> fieldNames = CollectionUtils.getFieldValues(fieldList, TableFieldInfo::getColumn);
        fieldNames.add(tableInfo.getKeyColumn());
        for (String checkField : checkFields) {
            if (fieldNames.stream().noneMatch(fieldName -> fieldName.equalsIgnoreCase(checkField))) {
                return checkField;
            }
        }
        return "";
    }

    public static void checkFieldsThrow(Class<?> clazz, List<String> checkField) {
        String res = FieldUtils.checkFields(clazz, checkField);
        if (StrUtil.isNotEmpty(res)) {
            throw new RuntimeException("不存在的列名:" + res);
        }
    }
}
