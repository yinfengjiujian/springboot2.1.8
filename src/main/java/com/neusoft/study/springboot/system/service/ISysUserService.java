package com.neusoft.study.springboot.system.service;

import com.neusoft.study.springboot.system.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.nio.file.LinkOption;
import java.util.List;

/**
 * <p>
 * 用户信息主表 服务类
 * </p>
 *
 * @author 段美林
 * @since 2019-10-06
 */
public interface ISysUserService extends IService<SysUser> {

    List<SysUser> getAllUser();

    SysUser getUserById(Long id);

    SysUser getUserByAccount(String account);

    SysUser getForUser();

}
