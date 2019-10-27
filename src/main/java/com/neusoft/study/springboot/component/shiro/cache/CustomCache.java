package com.neusoft.study.springboot.component.shiro.cache;

import com.neusoft.study.springboot.config.properties.UserProperties;
import com.neusoft.study.springboot.utils.JwtUtil;
import com.neusoft.study.springboot.utils.RedisServiceUtil;
import com.neusoft.study.springboot.utils.common.Constant;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;

/**
 * <p>Title: com.neusoft.study.springboot.component.shiro.cache</p>
 * <p>Company:东软集团(neusoft)</p>
 * <p>Copyright:Copyright(c)</p>
 * User: Administrator
 * Date: 2019/10/13 21:51
 * Description: No Description
 */
@Component
public class CustomCache<K,V> implements Cache<K,V> {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisServiceUtil redisServiceUtil;

    @Autowired
    private UserProperties userProperties;

    /**
     * 缓存的key名称获取为shiro:cache:account
     * @param key
     * @return java.lang.String
     * @author dolyw.com
     * @date 2018/9/4 18:33
     */
    private String getKey(Object key) {
        return Constant.PREFIX_SHIRO_CACHE + jwtUtil.getClaim(key.toString(), Constant.ACCOUNT);
    }

    /**
     * 获取缓存
     */
    @Override
    public Object get(Object key) throws CacheException {
        if (redisServiceUtil.hasKey(this.getKey(key))) {
            return redisServiceUtil.get(this.getKey(key));
        }
        return null;
    }

    /**
     * 保存缓存
     */
    @Override
    public Object put(Object key, Object value) throws CacheException {
        long shiroCacheExpireTime = userProperties.getShiroCacheExpireTime();
        // 设置Redis的Shiro缓存
        return redisServiceUtil.set(this.getKey(key), value, shiroCacheExpireTime);
    }

    /**
     * 移除缓存
     */
    @Override
    public Object remove(Object key) throws CacheException {
        if(redisServiceUtil.hasKey(this.getKey(key))){
            redisServiceUtil.del(this.getKey(key));
        }
        return null;
    }

    /**
     * 清空所有缓存
     */
    @Override
    public void clear() throws CacheException {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Set<K> keys() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }
}
