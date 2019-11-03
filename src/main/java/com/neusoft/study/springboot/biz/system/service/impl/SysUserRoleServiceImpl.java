package com.neusoft.study.springboot.biz.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neusoft.study.springboot.biz.system.entity.SysUserRole;
import com.neusoft.study.springboot.biz.system.entity.dto.UserRoleDto;
import com.neusoft.study.springboot.biz.system.mapper.SysUserRoleMapper;
import com.neusoft.study.springboot.biz.system.service.ISysUserRoleService;
import com.neusoft.study.springboot.common.CommonResult;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 用户角色关系表 服务实现类
 * </p>
 *
 * @author 段美林
 * @since 2019-10-13
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {

    @Override
    public List<SysUserRole> getSysUserRoles(Long userId) {
        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public CommonResult addUserRole(UserRoleDto userRoleDto) {
        SysUserRole sysUserRole = new SysUserRole();
        BeanUtils.copyProperties(userRoleDto,sysUserRole);
        baseMapper.insert(sysUserRole);
        return CommonResult.successResult("授权成功！");
    }
}
