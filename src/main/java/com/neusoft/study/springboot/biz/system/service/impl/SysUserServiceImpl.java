package com.neusoft.study.springboot.biz.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neusoft.study.springboot.biz.system.entity.*;
import com.neusoft.study.springboot.biz.system.entity.dto.UserDto;
import com.neusoft.study.springboot.biz.system.mapper.SysUserExtendMapper;
import com.neusoft.study.springboot.biz.system.mapper.SysUserMapper;
import com.neusoft.study.springboot.biz.system.service.ISysRoleService;
import com.neusoft.study.springboot.biz.system.service.ISysUserRoleService;
import com.neusoft.study.springboot.biz.system.service.ISysUserService;
import com.neusoft.study.springboot.common.CommonResult;
import com.neusoft.study.springboot.common.UserContext;
import com.neusoft.study.springboot.config.properties.UserProperties;
import com.neusoft.study.springboot.exception.BusinessException;
import com.neusoft.study.springboot.exception.enums.ErrorEnum;
import com.neusoft.study.springboot.utils.JwtUtil;
import com.neusoft.study.springboot.utils.PassWordUtils;
import com.neusoft.study.springboot.utils.RedisServiceUtil;
import com.neusoft.study.springboot.utils.common.Constant;
import com.neusoft.study.springboot.utils.common.LocalDateTimeUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户信息主表 服务实现类
 * </p>
 *
 * @author 段美林
 * @since 2019-10-08
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserProperties userProperties;

    @Autowired
    private ISysUserRoleService iSysUserRoleService;

    @Autowired
    private ISysRoleService iSysRoleService;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private RedisServiceUtil redisServiceUtil;

    @Autowired
    private SysUserExtendMapper sysUserExtendMapper;

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

    @Override
    public CommonResult login(UserDto userDto, HttpServletResponse response) {
        // 判断当前帐号是否存在
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account", userDto.getUserAccount());
        SysUser sysUserByAccount = sysUserMapper.selectOne(queryWrapper);
        if (Objects.isNull(sysUserByAccount)) {
            return CommonResult.warnResult(ErrorEnum.DUPLICATE_KEY_CODE.getEnumCode(),
                    "该帐号:【" + userDto.getUserAccount() + "】不存在(The account does not exist.)");
        }
        String passwdSalt = sysUserByAccount.getPasswdSalt();
        //将用户输入密码加盐（数据库中存入的盐），并且用md5算法加密三次，得到最后结果
        String passWordEntry = new Md5Hash(userDto.getPassWord(), passwdSalt, 3).toString();
        // 将用户输入的密码和数据库存储的盐，进行MD5加密后，与数据库保存的密码进行比对，如果相同则允许登录，否则错误不允许登录
        if (passWordEntry.equals(sysUserByAccount.getUserPasswd())) {
            // 清除可能存在的Shiro权限信息缓存
            if (redisServiceUtil.hasKey(Constant.PREFIX_SHIRO_CACHE + userDto.getUserAccount())) {
                redisServiceUtil.del(Constant.PREFIX_SHIRO_CACHE + userDto.getUserAccount());
            }

            // 获取用户的相关信息，存入redis中，方便用户后续直接取用
            UserInfo cacheUserInfo = this.getCacheUserInfo(sysUserByAccount.getUserAccount());
            String cache = JSON.toJSONString(cacheUserInfo);
            redisServiceUtil.set(Constant.PREFIX_LOGIN_CACHE + userDto.getUserAccount(),
                    cache,userProperties.getLoginPersionCacheExpireTime());

            // 设置RefreshToken，时间戳为当前时间戳，直接设置即可(不用先删后设，会覆盖已有的RefreshToken)
            String currentTimeMillis = String.valueOf(System.currentTimeMillis());
            redisServiceUtil.set(Constant.PREFIX_SHIRO_REFRESH_TOKEN + userDto.getUserAccount(),
                    currentTimeMillis, userProperties.getRefreshTokenExpireTime());
            // 从Header中Authorization返回AccessToken，时间戳为当前时间戳
            String token = jwtUtil.sign(userDto.getUserAccount(), currentTimeMillis);
            response.setHeader(userProperties.getTokenKey(), token);
            response.setHeader("Access-Control-Expose-Headers", userProperties.getTokenKey());
            return CommonResult.successResult("登录成功！");
        } else {
            return CommonResult.errorResult(ErrorEnum.LOGIN_ERROR_PASSWORD.getEnumCode(), ErrorEnum.LOGIN_ERROR_PASSWORD.getMessage());
        }
    }

    @Override
    public CommonResult getUserInfo() {
        UserInfo userInfo;
        String userAccount = UserContext.getUserAccount();
        //先从缓存中获取用户信息
        Object object = redisServiceUtil.get(Constant.PREFIX_LOGIN_CACHE + userAccount);
        if (Objects.isNull(object)) {
            userInfo = this.getCacheUserInfo(userAccount);
            redisServiceUtil.set(Constant.PREFIX_LOGIN_CACHE + userAccount,
                    JSON.toJSONString(userInfo),userProperties.getLoginPersionCacheExpireTime());
        } else {
            userInfo = JSON.parseObject((String) object, UserInfo.class);
        }
        return new CommonResult(userInfo);
    }

    @Override
    public CommonResult logout() {
        //拿到退出用户账号
        String userAccount = UserContext.getUserAccount();
        //删除redis缓存中的用户刷新token信息，使用后的访问token无效，即可退出登录
        if (redisServiceUtil.hasKey(Constant.PREFIX_SHIRO_REFRESH_TOKEN + userAccount)) {
            redisServiceUtil.del(Constant.PREFIX_SHIRO_REFRESH_TOKEN + userAccount);
        }
        return new CommonResult("退出成功！");
    }

    @Override
    public CommonResult addUser(UserDto userDto) {
        // 判断当前帐号是否存在
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account", userDto.getUserAccount());
        SysUser sysUserByAccount = sysUserMapper.selectOne(queryWrapper);
        if (Objects.nonNull(sysUserByAccount)) {
            return CommonResult.warnResult(ErrorEnum.DUPLICATE_KEY_CODE.getEnumCode(),
                    "该帐号:【" + userDto.getUserAccount() + "】已存在(Account exist)");
        }
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(userDto, sysUser);
        // 密码以MD5算法加密，并用随机16盐，散列三次存入数据库
        //生成盐（部分，需要存入数据库中）
        String salt = PassWordUtils.generateSalt();
        sysUser.setPasswdSalt(salt);
        //将原始密码加盐（上面生成的盐），并且用md5算法加密三次，将最后结果存入数据库中
        String passWordEntry = new Md5Hash(userDto.getPassWord(), salt, 3).toString();
        sysUser.setUserPasswd(passWordEntry);
        sysUser.setCreateTime(LocalDateTimeUtils.convertDateToLDT(new Date()));

        //处理用户扩展表
        long extendId = IdWorker.getId();
        sysUser.setUserExtId(extendId);
        SysUserExtend sysUserExtend = new SysUserExtend();
        BeanUtils.copyProperties(userDto, sysUserExtend);
        sysUserExtend.setId(extendId);
        try {
            //新增用户信息
            sysUserMapper.insert(sysUser);
            //新增用户扩展信息
            sysUserExtendMapper.insert(sysUserExtend);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("新增用户失败！" + e.getMessage());
        }
        return CommonResult.successResult("新增用户成功！");
    }

    @Override
    public UserInfo getCacheUserInfo(String account) {
        // 判断当前帐号是否存在
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account", account);
        SysUser sysUserByAccount = sysUserMapper.selectOne(queryWrapper);
        if (Objects.isNull(sysUserByAccount)) {
            return null;
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setUserAccount(sysUserByAccount.getUserAccount());
        userInfo.setUserName(sysUserByAccount.getUserName());
        userInfo.setUserId(sysUserByAccount.getId());
        userInfo.setUserTelephone(sysUserExtendMapper.selectById(sysUserByAccount.getUserExtId()).getTelephone());

        //根据用户Id获取用户角色关系集合
        List<SysUserRole> sysUserRoles = iSysUserRoleService.getSysUserRoles(sysUserByAccount.getId());
        if (CollectionUtils.isNotEmpty(sysUserRoles)) {
            List<Long> roleIdList = sysUserRoles.stream().map(sysUserRole -> sysUserRole.getRoleId()).collect(Collectors.toList());
            //根据角色ID集合获取角色列表集合
            List<SysRole> sysRoleList = iSysRoleService.getSysRoleList(roleIdList);
            userInfo.setRoleList(sysRoleList);
        }
        return userInfo;
    }

}
