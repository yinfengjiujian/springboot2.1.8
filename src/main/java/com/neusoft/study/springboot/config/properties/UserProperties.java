package com.neusoft.study.springboot.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * <p>Title: com.neusoft.study.springboot.config.properties</p>
 * <p>Company:东软集团(neusoft)</p>
 * <p>Copyright:Copyright(c)</p>
 * User: Administrator
 * Date: 2019/10/15 21:11
 * Description: 用户自定义配置属性
 */
@Configuration
@ConfigurationProperties(prefix="shiro")
@PropertySource(value="classpath:userCustom.yaml",factory = YamlPropertyLoaderFactory.class)
@Data
public class UserProperties {

    //Shiro缓存过期时间-5分钟-5*60(秒为单位)
    private Long shiroCacheExpireTime;

    //AES密码加密私钥(Base64加密)
    private String encryptAESKey;

    //JWT认证加密私钥(Base64加密)
    private String encryptJWTKey;

    //RefreshToken刷新token过期时间-30分钟-30*60(秒为单位)
    private Long refreshTokenExpireTime;

    //访问令牌过期时间
    private Long accessTokenExpireTime;

    //个人基本信息、角色信息缓存过期时间
    private Long loginPersionCacheExpireTime;

    private String tokenKey;
}
