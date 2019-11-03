package com.neusoft.study.springboot.component.shiro.cache.manager;

import com.neusoft.study.springboot.component.shiro.cache.factory.ShiroCacherFactory;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.stereotype.Component;

/**
 * <p>Title: com.neusoft.study.springboot.component.shiro.cache.manager</p>
 * <p>Company:东软集团(neusoft)</p>
 * <p>Copyright:Copyright(c)</p>
 * User: Administrator
 * Date: 2019/10/29 21:49
 * Description: No Description
 */
@Component
public class CustomCacheManager implements CacheManager {

    @Override
    public <Key, Value> Cache<Key, Value> getCache(String s) throws CacheException {
        return ShiroCacherFactory.getInstance().createShiroCache("redis");
    }
}
