package com.neusoft.study.springboot.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.neusoft.study.springboot.config.properties.UserProperties;
import com.neusoft.study.springboot.exception.BusinessException;
import com.neusoft.study.springboot.exception.enums.ErrorEnum;
import com.neusoft.study.springboot.utils.common.Base64ConvertUtil;
import com.neusoft.study.springboot.utils.common.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * <p>Title: com.neusoft.study.springboot.utils</p>
 * <p>Company:东软集团(neusoft)</p>
 * <p>Copyright:Copyright(c)</p>
 * User: Administrator
 * Date: 2019/10/13 20:16
 * Description: No Description
 */
@Component
@Slf4j
public class JwtUtil {

    @Autowired
    private UserProperties userProperties;

    /**
     * 校验token是否正确
     * @param token Token
     * @return boolean 是否正确
     * @author Wang926454
     * @date 2018/8/31 9:05
     */
    public  boolean verify(String token) {
        try {
            // 帐号加JWT私钥解密
            String secret = getClaim(token, Constant.ACCOUNT) + Base64ConvertUtil.decode(userProperties.getEncryptJWTKey());
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (UnsupportedEncodingException e) {
            log.error("JWTToken认证解密出现UnsupportedEncodingException异常:" + e.getMessage());
            throw new BusinessException(ErrorEnum.DECODED_JWTTOKEN_ERROR.getCode().toString(),
                    ErrorEnum.DECODED_JWTTOKEN_ERROR.getMessage());
        }
    }

    /**
     * 获得Token中的信息无需secret解密也能获得
     * @param token
     * @param claim
     * @return java.lang.String
     * @author Wang926454
     * @date 2018/9/7 16:54
     */
    public  String getClaim(String token, String claim) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            // 只能输出String类型，如果是其他类型返回null
            return jwt.getClaim(claim).asString();
        } catch (JWTDecodeException e) {
            log.error("解密Token中的公共信息出现JWTDecodeException异常:" + e.getMessage());
            throw new BusinessException(ErrorEnum.DECODED_JWTTOKEN_COMMON.getEnumCode(),
                    ErrorEnum.DECODED_JWTTOKEN_COMMON.getMessage());
        }
    }

    /**
     * 生成签名
     * @param account 帐号
     * @return java.lang.String 返回加密的Token
     * @author Wang926454
     * @date 2018/8/31 9:07
     */
    public  String sign(String account, String currentTimeMillis) {
        try {
            // 帐号加JWT私钥加密
            String secret = account + Base64ConvertUtil.decode(userProperties.getEncryptJWTKey());
            // 此处过期时间是以毫秒为单位，所以乘以1000
            Date date = new Date(System.currentTimeMillis() + userProperties.getAccessTokenExpireTime() * 1000);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            // 附带account帐号信息
            return JWT.create()
                    .withClaim(Constant.ACCOUNT, account)
                    .withClaim(Constant.CURRENT_TIME_MILLIS, currentTimeMillis)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            log.error("JWTToken加密出现UnsupportedEncodingException异常:" + e.getMessage());
            throw new BusinessException(ErrorEnum.ENCRYPT_JWTTOKEN_ERROR.getCode().toString(),
                    ErrorEnum.ENCRYPT_JWTTOKEN_ERROR.getMessage());
        }
    }

}
