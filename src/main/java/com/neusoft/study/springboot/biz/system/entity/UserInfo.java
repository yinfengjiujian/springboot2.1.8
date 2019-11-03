package com.neusoft.study.springboot.biz.system.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>Title: com.neusoft.study.springboot.biz.system.entity</p>
 * <p>Company:东软集团(neusoft)</p>
 * <p>Copyright:Copyright(c)</p>
 * User: Administrator
 * Date: 2019/11/2 16:00
 * Description: No Description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo implements Serializable {
    private Long userId;
    private String userAccount;
    private String userName;
    private String userTelephone;
    private List<SysRole> roleList;
}
