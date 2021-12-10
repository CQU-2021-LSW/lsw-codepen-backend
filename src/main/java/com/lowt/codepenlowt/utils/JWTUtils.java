package com.lowt.codepenlowt.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

public class JWTUtils {

    /**
     * 生成 token
     */

    public static String getToken(Map<String,String> map){
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE,1);

        JWTCreator.Builder builder = JWT.create();
        // payload
        map.forEach(builder::withClaim);
        // 过期时间和签名算法
        return builder.withExpiresAt(instance.getTime())
                .sign(Algorithm.HMAC256(JWTSaltValue.SALT_VALUE));
    }

    /**
     * 验证 token 且 获取信息
     */

    public static DecodedJWT verify(String token){
        return JWT.require(Algorithm.HMAC256(JWTSaltValue.SALT_VALUE)).build().verify(token);
    }

}
