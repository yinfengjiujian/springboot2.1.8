package com.neusoft.study.springboot.biz.system.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.neusoft.study.springboot.biz.system.entity.valid.group.UserLoginValid;
import com.neusoft.study.springboot.biz.system.entity.valid.group.UserRegistValid;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>Title: com.neusoft.study.springboot.biz.system.entity.dto</p>
 * <p>Company:东软集团(neusoft)</p>
 * <p>Copyright:Copyright(c)</p>
 * User: Administrator
 * Date: 2019/10/13 21:06
 * Description: No Description
 */
@Data
public class UserDto implements Serializable {

    @NotBlank(message = "用户账号不能为空！",groups = {UserLoginValid.class, UserRegistValid.class})
    @Length(max = 30,message = "用户账号不能超过30个字符")
    private String userAccount;

    @NotBlank(message = "用户密码不能为空",groups = {UserLoginValid.class,UserRegistValid.class})
    @Length(max = 50,message = "用户密码不能超过50个字符")
    private String passWord;

    @NotBlank(message = "用户密码不能为空",groups = {UserRegistValid.class})
    @Length(max = 50,message = "用户密码不能超过50个字符")
    private String userName;

    private Integer state;

    private Date createTime;

    @NotNull(message = "用户性别不能为空",groups = {UserRegistValid.class})
    private Integer userSex;

    private Integer userAge;

    @Email(message = "邮箱格式不对")
    private String email;

    private String adress;

    @NotBlank(message = "用户电话不能为空",groups = {UserRegistValid.class})
    @Pattern(regexp = "^[1][3,4,5,6,7,8,9][0-9]{9}$", message = "手机号格式有误")
    private String telephone;

    /**
     * 登录时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date loginTime;
}
