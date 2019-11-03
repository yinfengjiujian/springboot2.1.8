package com.neusoft.study.springboot.biz.system.controller;


import com.neusoft.study.springboot.biz.system.entity.dto.RoleDto;
import com.neusoft.study.springboot.biz.system.entity.dto.UserDto;
import com.neusoft.study.springboot.biz.system.entity.valid.group.UserRegistValid;
import com.neusoft.study.springboot.biz.system.service.ISysRoleService;
import com.neusoft.study.springboot.common.CommonResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户角色表 前端控制器
 * </p>
 *
 * @author 段美林
 * @since 2019-10-13
 */
@RestController
@RequestMapping("/system/sys-role")
public class SysRoleController {

    @Autowired
    private ISysRoleService iSysRoleService;

    /**
     * 添加角色
     * @param roleDto
     * @return
     */
    @ApiOperation(value = "用户添加角色",notes = "添加角色",httpMethod = "POST",response = CommonResult.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleDto",value = "用户角色信息传递对象",required = true)
    })
    @PostMapping(value = "/addRole")
    public CommonResult addRole(@Validated @RequestBody RoleDto roleDto) {
        CommonResult commonResult = iSysRoleService.addRole(roleDto);
        return commonResult;
    }

}

