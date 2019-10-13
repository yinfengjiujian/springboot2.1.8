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
 * 系统资源表
 * </p>
 *
 * @author 段美林
 * @since 2019-10-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("SYS_PERMISSION")
@ApiModel(value="SysPermission对象", description="系统资源表")
public class SysPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "资源ID")
    @TableId("id")
    private Long id;

    @ApiModelProperty(value = "父资源ID")
    @TableField("parent_id")
    private Long parentId;

    @ApiModelProperty(value = "菜单层级")
    @TableField("level")
    private Integer level;

    @ApiModelProperty(value = "权限字符串")
    @TableField("permission")
    private String permission;

    @ApiModelProperty(value = "资源名称")
    @TableField("permission_name")
    private String permissionName;

    @ApiModelProperty(value = "资源类型")
    @TableField("premission_type")
    private String premissionType;

    @ApiModelProperty(value = "资源URL")
    @TableField("url")
    private String url;

    @ApiModelProperty(value = "资源描述")
    @TableField("description")
    private String description;

    @ApiModelProperty(value = "是否有效")
    @TableField("available")
    private Integer available;


}
