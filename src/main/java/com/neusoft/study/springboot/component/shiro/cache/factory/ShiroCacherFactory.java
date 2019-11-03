package com.neusoft.study.springboot.component.shiro.cache.factory;

import com.neusoft.study.springboot.component.shiro.cache.ShiroCacheImpl;
import org.apache.shiro.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

/**
 * <p>Title: com.neusoft.study.springboot.component.shiro.cache</p>
 * <p>Company:东软集团(neusoft)</p>
 * <p>Copyright:Copyright(c)</p>
 * User: Administrator
 * Date: 2019/10/28 22:01
 * Description: No Description
 */
public class ShiroCacherFactory implements ShiroCache {

    @Autowired
    private ShiroCacheImpl shiroCache;

    private volatile static ShiroCacherFactory shiroCacherFactory;

    private ShiroCacherFactory() { }

    /**
     * 使用DCL双检查锁机制 实现线程安全
     * @return
     */
    public static ShiroCacherFactory getInstance() {
        if (Objects.nonNull(shiroCacherFactory)) {
            return shiroCacherFactory;
        } else {
            synchronized (ShiroCacherFactory.class) {
                if (Objects.isNull(shiroCacherFactory)) {
                    shiroCacherFactory = new ShiroCacherFactory();
                }
            }
        }
        return shiroCacherFactory;
    }

    /**
     * 缓存工厂生产类，返回真正的shiro缓存实现类
     * @param cacheType
     * @return
     */
    @Override
    public Cache createShiroCache(String cacheType) {
        if ("redis".equals(cacheType)){
            return shiroCache;
        }
        return null;
    }
}
