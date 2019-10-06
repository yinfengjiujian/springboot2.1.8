package com.neusoft.study.springboot.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户信息主表
 * </p>
 *
 * @author 段美林
 * @since 2019-10-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@Accessors(chain = true)
@TableName("SYS_USER")
@ApiModel(value="SysUser对象", description="用户信息主表")
public class SysUser implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "用户ID")
    @TableId("id")
    private Long id;

    @ApiModelProperty(value = "用户账号")
    @TableField("user_account")
    private String userAccount;

    @ApiModelProperty(value = "姓名")
    @TableField("user_name")
    private String userName;

    @ApiModelProperty(value = "用户密码")
    @TableField("user_passwd")
    private String userPasswd;

    @ApiModelProperty(value = "是否锁定")
    @TableField("is_locked")
    private Integer isLocked;

    @ApiModelProperty(value = "用户密码盐")
    @TableField("passwd_salt")
    private String passwdSalt;

    @ApiModelProperty(value = "用户扩展信息表ID")
    @TableField("user_ext_id")
    private Long userExtId;


}
