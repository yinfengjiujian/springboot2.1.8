package com.neusoft.study.springboot.config;

import com.neusoft.study.springboot.biz.system.entity.SysRole;
import com.neusoft.study.springboot.biz.system.entity.UserInfo;

import java.util.List;

/**
 * <p>Title: com.neusoft.study.springboot.config</p>
 * <p>Company:东软集团(neusoft)</p>
 * <p>Copyright:Copyright(c)</p>
 * User: Administrator
 * Date: 2019/11/2 15:58
 * Description: No Description
 */
public interface SubjectLoader {

    UserInfo getUserInfo();

    Long getUserId();

    String getUserName();

    String getUserAccount();

    String getUserTelephone();

    List<SysRole> getUserRoles();

    void setUserInfo(String account);

    void remove();
}
