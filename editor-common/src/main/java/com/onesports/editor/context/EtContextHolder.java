package com.onesports.editor.context;

import com.onesports.editor.exception.EtException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @program: odf-editor-system
 * @description: 项目上下文
 * @author: xjr
 * @create: 2020-07-21 10:52
 **/

public class EtContextHolder {

    public static final String REQUEST_ID = "requestId";
    public static final String OPERATOR = "operator";
    public static final String TOKEN = "token";


    private static final ThreadLocal<Map<String, String>> HOLDER = ThreadLocal.withInitial(
            () -> new HashMap<>(1)
    );

    public static String get(String key) {
        return HOLDER.get().getOrDefault(key, "");
    }

    public static String getOrThrow(String key) throws EtException {
        return Optional.ofNullable(get(key)).orElseThrow(() -> new EtException("找不到指定的上下文值,key=" + key));
    }

    public static void set(String key, String value) {
        HOLDER.get().put(key, value);
    }

    public static void clear() {
        HOLDER.remove();
    }
}
