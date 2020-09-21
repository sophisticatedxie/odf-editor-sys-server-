package com.onesports.editor.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.onesports.editor.context.EtContextHolder;
import com.onesports.editor.exception.EtException;

import java.util.Date;
import java.util.HashMap;

/**
 * @program: odf-editor-system
 * @description: JWT鉴权工具
 * @author: xjr
 * @create: 2020-07-21 11:28
 **/

public final class JwtUtil {
    /**
     * 过期时间为一天
     * TODO 正式上线更换为15分钟
     */
    private static final long EXPIRE_TIME = 60*1000*60*24*365;

    /**
     * token私钥
     */
    private static final String TOKEN_SECRET = "encryptUserId";

    /**
     * 生成签名,15分钟后过期
     * @param userId
     * @return
     */
    public static String sign(String userId){
        //过期时间
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        //私钥及加密算法
        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
        //设置头信息
        HashMap<String, Object> header = new HashMap<>(2);
        header.put("typ", "JWT");
        header.put("alg", "HS256");
        //附带username和userID生成签名
        return JWT.create().withHeader(header)
                .withClaim("userId",userId).withExpiresAt(date).sign(algorithm);
    }


    public static boolean verity(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        } catch (JWTVerificationException e) {
            return false;
        }

    }

    public static String decode(String token)throws EtException{
        if (!verity(token)){
            throw new EtException("token已经过期，请重新登录");
        }
        return JWT.decode(token).getClaim("userId").asString();
    }

    public static void main(String[] args) {
        System.out.println(JwtUtil.sign("xiaoxie"));
    }

    public static void setGlobalEmptyToken(){
        EtContextHolder.set(EtContextHolder.TOKEN,"");
        EtContextHolder.set(EtContextHolder.OPERATOR,"");
    }
}
