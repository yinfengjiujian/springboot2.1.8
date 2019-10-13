package com.neusoft.study.springboot.biz.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author 段美林
 * @since 2019-10-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("SYS_USER_EXTEND")
@ApiModel(value="SysUserExtend对象", description="")
public class SysUserExtend implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户扩展信息ID")
    @TableId("id")
    private Long id;

    @ApiModelProperty(value = "用户性别")
    @TableField("user_sex")
    private Integer userSex;

    @ApiModelProperty(value = "用户年龄")
    @TableField("user_age")
    private Integer userAge;

    @ApiModelProperty(value = "用户邮箱")
    @TableField("email")
    private String email;

    @ApiModelProperty(value = "用户地址")
    @TableField("adress")
    private String adress;

    @ApiModelProperty(value = "用户电话")
    @TableField("telephone")
    private String telephone;


}
