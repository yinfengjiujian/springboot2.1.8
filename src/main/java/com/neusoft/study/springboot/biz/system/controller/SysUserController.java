package com.neusoft.study.springboot.biz.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.neusoft.study.springboot.biz.system.entity.SysUser;
import com.neusoft.study.springboot.biz.system.service.ISysUserService;
import com.neusoft.study.springboot.common.CommonResult;
import com.neusoft.study.springboot.utils.RedisServiceUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    @Autowired
    private RedisServiceUtil redisServiceUtil;

    @ApiOperation(value = "获取所有用户信息",notes = "查询数据库中的记录",httpMethod = "GET",response = List.class)
    @RequestMapping(value = "/getAllUser",method = {RequestMethod.GET,RequestMethod.POST})
    public List<SysUser> getAllUser() {
        List<SysUser> allUser = iSysUserService.getAllUser();

        String toJSONString = JSONObject.toJSONString(allUser);

        redisServiceUtil.set("duanml",toJSONString);

        Map<String,Object> objectMap = new HashMap<>();
        objectMap.put("name","尹海燕");

        redisServiceUtil.hmset("name",objectMap);

        return allUser;
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
    public CommonResult getUserByAccount(String account) {
        SysUser userByAccount = iSysUserService.getUserByAccount(account);
        if (Objects.isNull(userByAccount)) {
            return CommonResult.warnResult("160001","没有查询到相关数据！");
        }
        return new CommonResult<>(userByAccount);
    }

    @ApiOperation(value = "获取用户自定义用户信息",notes = "直接类中返回，测试自定义方法，非Mybatis方法",httpMethod = "POST",response = SysUser.class)
    @RequestMapping(value = "/getForUser",method = {RequestMethod.GET,RequestMethod.POST})
    public SysUser getForUser() {
        return iSysUserService.getForUser();
    }


}
