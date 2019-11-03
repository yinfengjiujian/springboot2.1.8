package com.neusoft.study.springboot.common;

import com.neusoft.study.springboot.biz.system.entity.SysRole;
import com.neusoft.study.springboot.biz.system.entity.UserInfo;
import com.neusoft.study.springboot.config.SubjectLoader;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * <p>Title: com.neusoft.study.springboot.common</p>
 * <p>Company:东软集团(neusoft)</p>
 * <p>Copyright:Copyright(c)</p>
 * User: Administrator
 * Date: 2019/11/2 11:11
 * Description: No Description
 */
@Component
@NoArgsConstructor
public class UserContext implements ApplicationContextAware, InitializingBean {
    private static volatile ApplicationContext applicationContext = null;
    private static volatile SubjectLoader loader = null;

    public static Long getUserId() {
        return Objects.nonNull(loader) ? loader.getUserId() : null;
    }

    public static String getUserName() {
        return Objects.nonNull(loader) ? loader.getUserName() : null;
    }

    public static String getUserAccount() {
        return Objects.nonNull(loader) ? loader.getUserAccount() : null;
    }

    public static String getUserTelephone() {
        return Objects.nonNull(loader) ? loader.getUserTelephone() : null;
    }

    public static List<SysRole> getUserRoles() {
        return Objects.nonNull(loader) ? loader.getUserRoles() : null;
    }


    public static void setUserInfo(String account) {
        if (Objects.nonNull(loader)) {
            loader.setUserInfo(account);
        }
    }

    public static UserInfo getUserInfo() {
        return Objects.nonNull(loader) ? loader.getUserInfo() : null;
    }

    public static void remove() {
        if (Objects.nonNull(loader)) {
            loader.remove();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            loader = (SubjectLoader) applicationContext.getBean(SubjectLoader.class);
        } catch (BeansException e) {
            e.printStackTrace();
            loader = null;
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        UserContext.applicationContext = applicationContext;
    }
}
