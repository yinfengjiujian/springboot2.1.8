package com.neusoft.study.springboot.biz.system.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>Title: com.neusoft.study.springboot.biz.system.entity.dto</p>
 * <p>Company:东软集团(neusoft)</p>
 * <p>Copyright:Copyright(c)</p>
 * User: Administrator
 * Date: 2019/11/2 21:15
 * Description: No Description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleDto implements Serializable {

    @NotNull(message = "用户Id不能为空！")
    @Length(max = 25,message = "用户Id不能超过25个字符")
    private Long userId;

    @NotNull(message = "角色id不能为空！")
    @Length(max = 25,message = "角色id不能超过25个字符")
    private Long roleId;
}
