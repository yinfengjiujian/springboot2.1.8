package com.neusoft.study.springboot.ansy;

/**
 * <p>Title: com.neusoft.study.springboot.ansy</p>
 * <p>Company:东软集团(neusoft)</p>
 * <p>Copyright:Copyright(c)</p>
 * User: Administrator
 * Date: 2019/10/23 22:19
 * Description: No Description
 */
public class UserService {

    public UserInfo queryUserInfoById(String userId) throws Exception{
        if (userId.equals("126")) {
            throw new Exception("出错了");
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        userInfo.setUserName(userId + " yy " + "__" + Thread.currentThread().getName());
        userInfo.setNickName(userId + "nickName");
        System.out.println("--" + userInfo);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
        }
        return userInfo;
    }
}
