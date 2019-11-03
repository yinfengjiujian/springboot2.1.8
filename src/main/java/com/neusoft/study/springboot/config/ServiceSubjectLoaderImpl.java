package com.neusoft.study.springboot.config;

import com.alibaba.fastjson.JSON;
import com.neusoft.study.springboot.biz.system.entity.SysRole;
import com.neusoft.study.springboot.biz.system.entity.UserInfo;
import com.neusoft.study.springboot.biz.system.service.ISysUserService;
import com.neusoft.study.springboot.config.properties.UserProperties;
import com.neusoft.study.springboot.utils.RedisServiceUtil;
import com.neusoft.study.springboot.utils.common.Constant;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

/**
 * <p>Title: com.neusoft.study.springboot.config</p>
 * <p>Company:东软集团(neusoft)</p>
 * <p>Copyright:Copyright(c)</p>
 * User: Administrator
 * Date: 2019/11/2 16:14
 * Description: No Description
 */
@NoArgsConstructor
public class ServiceSubjectLoaderImpl implements SubjectLoader {
    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    @Autowired
    private RedisServiceUtil redisServiceUtil;
    @Autowired
    private ISysUserService iSysUserService;
    @Autowired
    private UserProperties userProperties;

    @Override
    public void setUserInfo(String account) {
        threadLocal.set(account);
    }

    @Override
    public UserInfo getUserInfo() {
        String account = threadLocal.get();
        //先从缓存中去获取
        Object object = redisServiceUtil.get(Constant.PREFIX_LOGIN_CACHE + account);
        //如果缓存中没有，为空，则查询数据库
        if (Objects.isNull(object)) {
            //查询数据库返回用户信息
            UserInfo cacheUserInfo = iSysUserService.getCacheUserInfo(account);
            //再次放入缓存中
            redisServiceUtil.set(Constant.PREFIX_LOGIN_CACHE + account,cacheUserInfo,
                    userProperties.getLoginPersionCacheExpireTime());
            return cacheUserInfo;
        } else {
            //缓存中存在，直接返回用户信息
            UserInfo userInfo = JSON.parseObject((String) object, UserInfo.class);
            return userInfo;
        }
    }

    @Override
    public Long getUserId() {
        UserInfo userInfo = this.getUserInfo();
        return Objects.nonNull(userInfo) ? userInfo.getUserId() : null;
    }

    @Override
    public String getUserName() {
        UserInfo userInfo = this.getUserInfo();
        return Objects.nonNull(userInfo) ? userInfo.getUserName() : null;
    }

    @Override
    public String getUserAccount() {
        UserInfo userInfo = this.getUserInfo();
        return Objects.nonNull(userInfo) ? userInfo.getUserAccount() : null;
    }

    @Override
    public String getUserTelephone() {
        UserInfo userInfo = this.getUserInfo();
        return Objects.nonNull(userInfo) ? userInfo.getUserTelephone() : null;
    }

    @Override
    public List<SysRole> getUserRoles() {
        UserInfo userInfo = this.getUserInfo();
        return Objects.nonNull(userInfo) ? userInfo.getRoleList() : null;
    }

    @Override
    public void remove() {
        threadLocal.set(null);
        threadLocal.remove();
    }
}
