package com.onesports.editor.utils;

import cn.hutool.core.io.IoUtil;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @program: odf-editor-system
 * @description: 自定义加密算法
 * @author: xjr
 * @create: 2020-07-23 15:18
 **/

public final class CustomCryptoUtil {
    private static String encryptJsFunc="";

    private volatile static ScriptEngine scriptEngine;

    static {
        Resource classPathResource=new ClassPathResource("crypto/index.js");
        InputStream is ;
        scriptEngine=getJsEngine();
        try {
            is=classPathResource.getInputStream();
            encryptJsFunc=IoUtil.read(is,"UTF-8");
            is.close();
            scriptEngine.eval(encryptJsFunc);
        } catch (ScriptException | IOException e) {
            e.printStackTrace();
        }
    }


    /**
     *
     * @description 加密
     * @param sourceContent: 代加密算法
     * @return  密文
     * @author xiejiarong
     * @date 2020年07月23日 15:33
     */
    public static String encrypt(String sourceContent) throws ScriptException, NoSuchMethodException {
        Invocable invocable= (Invocable) scriptEngine;
        String result= (String) invocable.invokeFunction("encrypto",sourceContent,123,16);
        return result;
    }

    /**
     *
     * @description 方法描述
     * @param encryptCode: 加密的密文
     * @return 明文
     * @author xiejiarong
     * @date 2020年07月23日 15:42
     */
    public static String decrypt(String encryptCode) throws ScriptException, NoSuchMethodException {
        Invocable invocable= (Invocable) scriptEngine;
        String result= (String) invocable.invokeFunction("decrypto",encryptCode,123,16);
        return result;
    }


    /**
     *
     * @description 方法描述
     * @return javascript脚本执行引擎对象(单例)
     * @author xiejiarong
     * @date 2020年07月23日 15:37
     */
    public static ScriptEngine getJsEngine(){
        if (scriptEngine==null){
            synchronized (CustomCryptoUtil.class){
                if (scriptEngine==null){
                    scriptEngine=new ScriptEngineManager().getEngineByName("ecmascript");
                }
            }
        }
        return scriptEngine;
    }


    public static void main(String[] args) throws ScriptException, NoSuchMethodException {
        String course="0q59q18q9q1eq1aqfq1eq39q2q59q41q59q3q12q1eq11q9q59q57q59qfq1eq16qbq17q1aqfq1eq35q1aq16q1eq59q41q59q8d20q7a70q6a5aq6704q59q57q59qfq1eq16qbq17q1aqfq1eq38q14q15qfq1eq15qfq59q41q15qeq17q17q57q59qfq1eq16qbq17q1aqfq1eq32q1fq59q41q59q1dq19q42q4dq18q1aq4cq18q49q4bq1fq1dq4eq49q18q4dq18q49q4bq4dq1aq1fq43q19q1aq4dq1fq42q19q4eq49q4eq59q6";

        String decode=decrypt(course);
        System.out.println("解密后的明文:"+decode);
    }
}
