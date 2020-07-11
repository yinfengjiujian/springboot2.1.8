package com.neusoft.study.springboot.biz.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.neusoft.study.springboot.biz.system.entity.SysUser;
import com.neusoft.study.springboot.biz.system.entity.condition.UserCondition;
import com.neusoft.study.springboot.biz.system.entity.dto.UserDto;
import com.neusoft.study.springboot.biz.system.entity.valid.group.UserLoginValid;
import com.neusoft.study.springboot.biz.system.entity.valid.group.UserRegistValid;
import com.neusoft.study.springboot.biz.system.entity.vo.UserVo;
import com.neusoft.study.springboot.biz.system.service.ISysUserService;
import com.neusoft.study.springboot.common.CommonResult;
import com.neusoft.study.springboot.utils.RedisServiceUtil;
import io.goeasy.GoEasy;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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
    @Autowired
    private GoEasy goEasy;

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

    @ApiOperation(value = "根据用户账号获取用户的信息",notes = "查询数据库中的记录，自定义XML中的方法",httpMethod = "POST",response = CommonResult.class)
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

    @ApiOperation(value = "用户登录操作",notes = "用户登录",httpMethod = "POST",response = CommonResult.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userDto",value = "用户登录信息对象",required = true),
            @ApiImplicitParam(name = "response",value = "登录成功以后，用于返回用户信息",required = true)
    })
    @PostMapping("/login")
    public CommonResult login(@RequestBody @Validated(UserLoginValid.class) UserDto userDto,
                              HttpServletResponse response) {
        return iSysUserService.login(userDto,response);
    }

    @ApiOperation(value = "用户退出操作",notes = "用户退出",httpMethod = "POST",response = CommonResult.class)
    @PostMapping("/logout")
    public CommonResult logout() {
        return iSysUserService.logout();
    }

    /**
     * 用户注册操作
     * @param userDto
     * @return
     */
    @ApiOperation(value = "用户注册操作",notes = "用户注册",httpMethod = "POST",response = CommonResult.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userDto",value = "用户注册信息传递对象",required = true)
    })
    @PostMapping(value = "/regist")
    public CommonResult addUser(@Validated(UserRegistValid.class) @RequestBody UserDto userDto) {
        CommonResult commonResult = iSysUserService.addUser(userDto);
        return commonResult;
    }

    /**
     * 获取用户信息
     * @return
     */
    @ApiOperation(value = "获取用户信息",notes = "获取用户信息",httpMethod = "POST",response = CommonResult.class)
    @GetMapping(value = "/getInfo")
    public CommonResult getUserInfo() {
        return iSysUserService.getUserInfo();
    }

    @RequestMapping(value = "/getUserListByCondition")
    public List<UserVo> getUserListByCondition(@RequestBody UserCondition userCondition){
        return iSysUserService.getUserListByCondition(userCondition);
    }

    @RequestMapping(value = "/sendMessage")
    public void sendMessage(String message) {
        System.out.println("============");
        goEasy.publish("testChannel","消息来了，看到了吗？" + message);
    }


}
