package com.neusoft.study.springboot.config;

import com.neusoft.study.springboot.common.UserContext;
import com.neusoft.study.springboot.utils.JwtUtil;
import com.neusoft.study.springboot.utils.RedisServiceUtil;
import com.neusoft.study.springboot.utils.common.Constant;
import com.neusoft.study.springboot.utils.common.SpringUtils;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * <p>Title: com.neusoft.study.springboot.config</p>
 * <p>Company:东软集团(neusoft)</p>
 * <p>Copyright:Copyright(c)</p>
 * User: Administrator
 * Date: 2019/11/2 15:25
 * Description: No Description
 */
@NoArgsConstructor
public class JwtMvcInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        if (StringUtils.isNotBlank(token)) {
            //拿到当前登录的用户账号
            String account = SpringUtils.getBean(JwtUtil.class).getClaim(token, Constant.ACCOUNT);
            UserContext.setUserInfo(account);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
        UserContext.remove();
    }
}
