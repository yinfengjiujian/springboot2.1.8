package com.neusoft.study.springboot.biz.system.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * <p>Title: com.neusoft.study.springboot.biz.system.entity.dto</p>
 * <p>Company:东软集团(neusoft)</p>
 * <p>Copyright:Copyright(c)</p>
 * User: Administrator
 * Date: 2019/10/13 21:11
 * Description: No Description
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto implements Serializable {

    @NotBlank(message = "用户角色代码不能为空！")
    @Length(max = 50,message = "用户账号不能超过50个字符")
    private String roleName;

    @NotBlank(message = "用户角色描述不能为空！")
    @Length(max = 50,message = "用户角色描述不能超过50个字符")
    private String roleDescription;
}
