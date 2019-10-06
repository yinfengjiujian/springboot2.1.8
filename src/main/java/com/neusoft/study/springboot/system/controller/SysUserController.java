package com.neusoft.study.springboot.system.controller;


import com.neusoft.study.springboot.system.entity.SysUser;
import com.neusoft.study.springboot.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 用户信息主表 前端控制器
 * </p>
 *
 * @author 段美林
 * @since 2019-10-06
 */
@Api(value = "用户管理控制器")
@RestController
@RequestMapping("/system/sys-user")
public class SysUserController {

    @Autowired
    private ISysUserService iSysUserService;

    @ApiOperation(value = "获取所有用户信息",notes = "查询数据库中的记录",httpMethod = "GET",response = List.class)
    @RequestMapping(value = "/getAllUser",method = {RequestMethod.GET,RequestMethod.POST})
    public List<SysUser> getAllUser() {
        return iSysUserService.getAllUser();
    }

    @ApiOperation(value = "根据用户ID获取用户的信息",notes = "查询数据库中的记录",httpMethod = "POST",response = SysUser.class)
    @ApiImplicitParam(name = "id",value = "用户ID",required = true,dataType = "String",paramType = "query")
    @RequestMapping(value = "/getUserById",method = {RequestMethod.GET,RequestMethod.POST})
    public SysUser getUserById(Long id) {
        return iSysUserService.getUserById(id);
    }

    @ApiOperation(value = "根据用户账号获取用户的信息",notes = "查询数据库中的记录，自定义XML中的方法",httpMethod = "POST",response = SysUser.class)
    @ApiImplicitParam(name = "account",value = "用户账号",required = true,dataType = "String",paramType = "query")
    @RequestMapping(value = "/getUserByAccount",method = {RequestMethod.GET,RequestMethod.POST})
    public SysUser getUserByAccount(String account) {
        return iSysUserService.getUserByAccount(account);
    }



    @ApiOperation(value = "获取用户自定义用户信息",notes = "直接类中返回，测试自定义方法，非Mybatis方法",httpMethod = "POST",response = SysUser.class)
    @RequestMapping(value = "/getForUser",method = {RequestMethod.GET,RequestMethod.POST})
    public SysUser getForUser() {
        return iSysUserService.getForUser();
    }


}

