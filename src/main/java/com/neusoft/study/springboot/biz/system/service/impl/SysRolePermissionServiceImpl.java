package com.neusoft.study.springboot.biz.system.service.impl;

import com.neusoft.study.springboot.biz.system.entity.SysRolePermission;
import com.neusoft.study.springboot.biz.system.mapper.SysRolePermissionMapper;
import com.neusoft.study.springboot.biz.system.service.ISysRolePermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 角色与系统资源关系表 服务实现类
 * </p>
 *
 * @author 段美林
 * @since 2019-10-13
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class SysRolePermissionServiceImpl extends ServiceImpl<SysRolePermissionMapper, SysRolePermission> implements ISysRolePermissionService {

}
