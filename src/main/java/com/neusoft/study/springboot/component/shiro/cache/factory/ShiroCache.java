package com.neusoft.study.springboot.component.shiro.cache.factory;

import org.apache.shiro.cache.Cache;

/**
 * <p>Title: com.neusoft.study.springboot.component.shiro.cache</p>
 * <p>Company:东软集团(neusoft)</p>
 * <p>Copyright:Copyright(c)</p>
 * User: Administrator
 * Date: 2019/10/28 21:57
 * Description: No Description
 */
public interface ShiroCache {

    Cache createShiroCache(String cacheType);

}
