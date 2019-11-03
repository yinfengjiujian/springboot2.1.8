package com.neusoft.study.springboot.biz.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neusoft.study.springboot.biz.system.entity.SysRole;
import com.neusoft.study.springboot.biz.system.entity.dto.RoleDto;
import com.neusoft.study.springboot.common.CommonResult;

import java.util.List;

/**
 * <p>
 * 用户角色表 服务类
 * </p>
 *
 * @author 段美林
 * @since 2019-10-13
 */
public interface ISysRoleService extends IService<SysRole> {

    CommonResult addRole(RoleDto roleDto);

    List<SysRole> getSysRoleList(List<Long> roleIdList);

}
