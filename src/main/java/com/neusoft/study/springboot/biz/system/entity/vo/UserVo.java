package com.neusoft.study.springboot.biz.system.entity.vo;

import lombok.Data;

/**
 * <p>Title: com.neusoft.study.springboot.biz.system.entity.vo</p>
 * <p>Company:东软集团(neusoft)</p>
 * <p>Copyright:Copyright(c)</p>
 * User: Administrator
 * Date: 2019/11/13 21:47
 * Description: No Description
 */
@Data
public class UserVo {

    private String userAccount;
    private String userName;
    private String userSex;
    private Integer userAge;
    private String adress;
    private String telephone;
    private Integer state;
}
