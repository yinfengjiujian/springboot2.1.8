package com.neusoft.study.springboot.biz.system.service.impl;

import com.neusoft.study.springboot.biz.system.entity.SysUserExtend;
import com.neusoft.study.springboot.biz.system.mapper.SysUserExtendMapper;
import com.neusoft.study.springboot.biz.system.service.ISysUserExtendService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 段美林
 * @since 2019-10-13
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class SysUserExtendServiceImpl extends ServiceImpl<SysUserExtendMapper, SysUserExtend> implements ISysUserExtendService {

    @Override
    public SysUserExtend getSysUserExtendById(Long userExtendId) {
        return baseMapper.selectById(userExtendId);
    }
}
