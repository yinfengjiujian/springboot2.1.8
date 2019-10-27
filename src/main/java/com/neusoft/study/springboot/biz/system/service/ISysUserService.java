package com.neusoft.study.springboot.biz.system.service;

import com.neusoft.study.springboot.biz.system.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.neusoft.study.springboot.biz.system.entity.dto.UserDto;
import com.neusoft.study.springboot.common.CommonResult;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 用户信息主表 服务类
 * </p>
 *
 * @author 段美林
 * @since 2019-10-08
 */
public interface ISysUserService extends IService<SysUser> {

    List<SysUser> getAllUser();

    SysUser getUserById(Long id);

    SysUser getUserByAccount(String account);

    SysUser getForUser();

    CommonResult addUser(UserDto userDto);

    CommonResult login(UserDto userDto,HttpServletResponse response);

}
