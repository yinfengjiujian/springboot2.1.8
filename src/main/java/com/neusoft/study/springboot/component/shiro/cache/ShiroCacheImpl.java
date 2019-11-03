package com.neusoft.study.springboot.component.shiro.cache;

import com.neusoft.study.springboot.config.properties.UserProperties;
import com.neusoft.study.springboot.exception.BusinessException;
import com.neusoft.study.springboot.utils.JwtUtil;
import com.neusoft.study.springboot.utils.RedisServiceUtil;
import com.neusoft.study.springboot.utils.common.Constant;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

/**
 * <p>Title: com.neusoft.study.springboot.component.shiro.cache</p>
 * <p>Company:东软集团(neusoft)</p>
 * <p>Copyright:Copyright(c)</p>
 * User: Administrator
 * Date: 2019/10/29 21:44
 * Description: No Description
 */
@Component
@NoArgsConstructor
@AllArgsConstructor
public class ShiroCacheImpl<key,value> implements Cache<key,value> {

    @Autowired
    private RedisServiceUtil redisServiceUtil;

    @Autowired
    private UserProperties userProperties;

    private String getKey(Object key) {
        if (Objects.isNull(key)) {
            throw new BusinessException("缓存的Key为空，业务异常！");
        }
        return Constant.PREFIX_SHIRO_CACHE + key.toString();
    }

    @Override
    public Object get(Object key) throws CacheException {
        if (redisServiceUtil.hasKey(this.getKey(key))) {
            redisServiceUtil.get(this.getKey(key));
        }
        return null;
    }

    @Override
    public Object put(Object key, Object value) throws CacheException {
        Long shiroCacheExpireTime = userProperties.getShiroCacheExpireTime();
        return redisServiceUtil.set(getKey(key),value,shiroCacheExpireTime);
    }

    @Override
    public Object remove(Object key) throws CacheException {
        if (redisServiceUtil.hasKey(this.getKey(key))) {
            redisServiceUtil.del(this.getKey(key));
        }
        return null;
    }

    @Override
    public void clear() throws CacheException {
        redisServiceUtil.clearShiroAll(Constant.PREFIX_SHIRO_CACHE);
    }

    @Override
    public int size() {
        return redisServiceUtil.getShiroSize(Constant.PREFIX_SHIRO_CACHE);
    }

    @Override
    public Set keys() {
        return redisServiceUtil.getShiroAllKey(Constant.PREFIX_SHIRO_CACHE);
    }

    @Override
    public Collection values() {
        return redisServiceUtil.getShiroAllValue(Constant.PREFIX_SHIRO_CACHE);
    }
}
