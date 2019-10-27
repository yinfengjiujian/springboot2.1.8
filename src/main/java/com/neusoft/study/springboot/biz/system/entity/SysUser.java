package com.neusoft.study.springboot.biz.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 用户信息主表
 * </p>
 *
 * @author 段美林
 * @since 2019-10-13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("SYS_USER")
@ApiModel(value="SysUser对象", description="用户信息主表")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

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

    @ApiModelProperty(value = "用户密码盐")
    @TableField("passwd_salt")
    private String passwdSalt;

    @ApiModelProperty(value = "用户状态")
    @TableField("state")
    private Integer state;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField("modify_time")
    private LocalDateTime modifyTime;

    @ApiModelProperty(value = "用户扩展信息表ID")
    @TableField("user_ext_id")
    private Long userExtId;


}
