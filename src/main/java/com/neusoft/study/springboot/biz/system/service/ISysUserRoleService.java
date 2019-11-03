package com.neusoft.study.springboot.biz.system.service;

import com.neusoft.study.springboot.biz.system.entity.SysUserRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.neusoft.study.springboot.biz.system.entity.dto.UserRoleDto;
import com.neusoft.study.springboot.common.CommonResult;

import java.util.List;

/**
 * <p>
 * 用户角色关系表 服务类
 * </p>
 *
 * @author 段美林
 * @since 2019-10-13
 */
public interface ISysUserRoleService extends IService<SysUserRole> {

    List<SysUserRole> getSysUserRoles(Long userId);

    CommonResult addUserRole(UserRoleDto userRoleDto);

}
