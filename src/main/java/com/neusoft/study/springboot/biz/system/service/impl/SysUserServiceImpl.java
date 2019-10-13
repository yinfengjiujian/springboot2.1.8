package com.neusoft.study.springboot.biz.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.neusoft.study.springboot.biz.system.entity.SysUser;
import com.neusoft.study.springboot.biz.system.mapper.SysUserMapper;
import com.neusoft.study.springboot.biz.system.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户信息主表 服务实现类
 * </p>
 *
 * @author 段美林
 * @since 2019-10-08
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public List<SysUser> getAllUser() {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public SysUser getUserById(Long id) {
        return sysUserMapper.selectById(id);
    }

    @Override
    public SysUser getUserByAccount(String account) {
        return sysUserMapper.getUserByAccount(account);
    }

    @Override
    public SysUser getForUser() {
        return SysUser.builder().id(21L).state(2).userAccount("duanml").userName("段美林").userPasswd("1qaz@WSX").build();
    }

}
