package com.neusoft.study.springboot.biz.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDate;
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
 * 用户角色表
 * </p>
 *
 * @author 段美林
 * @since 2019-10-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("SYS_ROLE")
@ApiModel(value="SysRole对象", description="用户角色表")
public class SysRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色ID")
    @TableId("id")
    private Long id;

    @ApiModelProperty(value = "角色代码")
    @TableField("role_name")
    private String roleName;

    @ApiModelProperty(value = "角色描述")
    @TableField("role_description")
    private String roleDescription;

    @ApiModelProperty(value = "创建人ID")
    @TableField("create_auth")
    private Long createAuth;

    @ApiModelProperty(value = "创建人姓名")
    @TableField("create_name")
    private String createName;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private LocalDate createTime;

    @ApiModelProperty(value = "修改人ID")
    @TableField("modify_auth")
    private Long modifyAuth;

    @ApiModelProperty(value = "修改人姓名")
    @TableField("modify_name")
    private String modifyName;

    @ApiModelProperty(value = "修改时间")
    @TableField("modify_time")
    private LocalDate modifyTime;

    @ApiModelProperty(value = "是否有效")
    @TableField("available")
    private Integer available;


}
