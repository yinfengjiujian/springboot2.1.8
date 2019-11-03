package com.neusoft.study.springboot.biz.system.controller;


import com.neusoft.study.springboot.biz.system.entity.dto.UserRoleDto;
import com.neusoft.study.springboot.biz.system.service.ISysUserRoleService;
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
 * 用户角色关系表 前端控制器
 * </p>
 *
 * @author 段美林
 * @since 2019-10-13
 */
@RestController
@RequestMapping("/system/sys-user-role")
public class SysUserRoleController {

    @Autowired
    private ISysUserRoleService iSysUserRoleService;

    /**
     * 添加角色
     * @param userRoleDto
     * @return
     */
    @ApiOperation(value = "用户添加角色",notes = "添加角色",httpMethod = "POST",response = CommonResult.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userRoleDto",value = "用户角色信息传递对象",required = true)
    })
    @PostMapping(value = "/addUserRole")
    public CommonResult addUserRole(@RequestBody UserRoleDto userRoleDto) {
        CommonResult commonResult = iSysUserRoleService.addUserRole(userRoleDto);
        return commonResult;
    }

}

