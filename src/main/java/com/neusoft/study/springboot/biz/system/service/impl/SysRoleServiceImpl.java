package com.neusoft.study.springboot.biz.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neusoft.study.springboot.biz.system.entity.SysRole;
import com.neusoft.study.springboot.biz.system.entity.dto.RoleDto;
import com.neusoft.study.springboot.biz.system.mapper.SysRoleMapper;
import com.neusoft.study.springboot.biz.system.service.ISysRoleService;
import com.neusoft.study.springboot.common.CommonResult;
import com.neusoft.study.springboot.common.UserContext;
import com.neusoft.study.springboot.utils.common.LocalDateTimeUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户角色表 服务实现类
 * </p>
 *
 * @author 段美林
 * @since 2019-10-13
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Override
    public CommonResult addRole(RoleDto roleDto) {
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(roleDto,sysRole);
        sysRole.setCreateAuth(UserContext.getUserId());
        sysRole.setCreateName(UserContext.getUserName());
        sysRole.setCreateTime(LocalDateTimeUtils.convertDateToLDT(new Date()));
        baseMapper.insert(sysRole);
        return CommonResult.successResult("新增角色成功！");
    }

    @Override
    public List<SysRole> getSysRoleList(List<Long> roleIdList) {
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id",roleIdList);
        return baseMapper.selectList(queryWrapper);
    }
}
