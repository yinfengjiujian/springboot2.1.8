package com.neusoft.study.springboot.biz.system.mapper;

import com.neusoft.study.springboot.biz.system.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neusoft.study.springboot.biz.system.entity.condition.UserCondition;
import com.neusoft.study.springboot.biz.system.entity.dto.UserDto;
import com.neusoft.study.springboot.biz.system.entity.vo.UserVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 用户信息主表 Mapper 接口
 * </p>
 *
 * @author 段美林
 * @since 2019-10-08
 */
@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {

    SysUser getUserByAccount(@Param("account") String account);

    List<UserVo> getUserListByCondition(UserCondition userCondition);
}
