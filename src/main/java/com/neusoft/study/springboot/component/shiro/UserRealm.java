package com.neusoft.study.springboot.component.shiro;

import com.neusoft.study.springboot.biz.system.entity.SysUser;
import com.neusoft.study.springboot.biz.system.entity.dto.PermissionDto;
import com.neusoft.study.springboot.biz.system.entity.dto.RoleDto;
import com.neusoft.study.springboot.biz.system.entity.dto.UserDto;
import com.neusoft.study.springboot.biz.system.service.ISysUserService;
import com.neusoft.study.springboot.component.shiro.jwt.JwtToken;
import com.neusoft.study.springboot.utils.JwtUtil;
import com.neusoft.study.springboot.utils.RedisServiceUtil;
import com.neusoft.study.springboot.utils.common.Constant;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>Title: com.neusoft.study.springboot.component.shiro</p>
 * <p>Company:东软集团(neusoft)</p>
 * <p>Copyright:Copyright(c)</p>
 * User: Administrator
 * Date: 2019/10/13 19:40
 * Description: 自定义Realm，用于登录验证，用户授权
 */
@Service
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private RedisServiceUtil redisServiceUtil;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ISysUserService iSysUserService;

    /**
     * 大坑，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken authenticationToken) {
        return authenticationToken instanceof JwtToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        String account = jwtUtil.getClaim(principalCollection.toString(), Constant.ACCOUNT);
        UserDto userDto = new UserDto();
        userDto.setUserAccount(account);
        // 查询用户角色
        //TODO 增加角色数据库查询
//        List<RoleDto> roleDtos = roleMapper.findRoleByUser(userDto);
        List<RoleDto> roleDtos = new ArrayList<>();
        for (RoleDto roleDto : roleDtos) {
            if (roleDto != null) {
                // 添加角色
                simpleAuthorizationInfo.addRole(roleDto.getRoleName());
                // 根据用户角色查询权限
                //TODO 增加资源数据库查询
//                List<PermissionDto> permissionDtos = permissionMapper.findPermissionByRole(roleDto);
                List<PermissionDto> permissionDtos = new ArrayList<>();
                for (PermissionDto permissionDto : permissionDtos) {
                    if (permissionDto != null) {
                        // 添加权限
                        simpleAuthorizationInfo.addStringPermission(permissionDto.getPermission());
                    }
                }
            }
        }
        return simpleAuthorizationInfo;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = (String) authenticationToken.getCredentials();
        // 解密获得account，用于和数据库进行对比
        String account = jwtUtil.getClaim(token, Constant.ACCOUNT);
        // 帐号为空
        if (StringUtils.isBlank(account)) {
            throw new AuthenticationException("Token中帐号为空(The account in Token is empty.)");
        }
        // 查询用户是否存在
        SysUser userByAccount = iSysUserService.getUserByAccount(account);
        if (Objects.isNull(userByAccount)) {
            throw new AuthenticationException("该帐号不存在(The account does not exist.)");
        }
        // 开始认证，要AccessToken认证通过，且Redis中存在RefreshToken，且两个Token时间戳一致
        if (jwtUtil.verify(token) && redisServiceUtil.hasKey(Constant.PREFIX_SHIRO_REFRESH_TOKEN + account)) {
            // 获取RefreshToken的时间戳
            String currentTimeMillisRedis = redisServiceUtil.get(Constant.PREFIX_SHIRO_REFRESH_TOKEN + account).toString();
            // 获取AccessToken时间戳，与RefreshToken的时间戳对比
            if (jwtUtil.getClaim(token, Constant.CURRENT_TIME_MILLIS).equals(currentTimeMillisRedis)) {
                return new SimpleAuthenticationInfo(token, token, "userRealm");
            }
        }
        throw new AuthenticationException("Token已过期(Token expired or incorrect.)");
    }
}
