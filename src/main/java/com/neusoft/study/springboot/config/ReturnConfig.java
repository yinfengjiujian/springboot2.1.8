package com.neusoft.study.springboot.config;

import com.neusoft.study.springboot.common.CommonResult;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * <p>Title: com.neusoft.study.springboot.config</p>
 * <p>Company:东软集团(neusoft)</p>
 * <p>Copyright:Copyright(c)</p>
 * User: Administrator
 * Date: 2019/10/4 9:42
 * Description: 统一返回数据格式、异常统一返回格式
 */
@EnableWebMvc
@Configuration
public class ReturnConfig {

    @RestControllerAdvice("com.neusoft.study.springboot")
    static class CommonResultResponseAdvice implements ResponseBodyAdvice<Object> {

        @Override
        public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
            return true;
        }

        @Override
        public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType,
                                      Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest,
                                      ServerHttpResponse serverHttpResponse) {
            if (body instanceof CommonResult) {
                return body;
            }
            return new CommonResult<Object>(body);
        }
    }
}
