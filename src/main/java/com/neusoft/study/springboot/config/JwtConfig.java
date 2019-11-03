package com.neusoft.study.springboot.config;

import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p>Title: com.neusoft.study.springboot.config</p>
 * <p>Company:东软集团(neusoft)</p>
 * <p>Copyright:Copyright(c)</p>
 * User: Administrator
 * Date: 2019/11/2 14:59
 * Description: No Description
 */
@Configuration
@NoArgsConstructor
@ConditionalOnProperty(prefix = "jwt", name = "jwt-open", havingValue = "true", matchIfMissing = true)
public class JwtConfig implements WebMvcConfigurer {

    public void addInterceptors(InterceptorRegistry interceptorRegistry) {
        interceptorRegistry.addInterceptor(new JwtMvcInterceptor());
    }

    @Bean
    public SubjectLoader serviceSubjectLoader() {
        return new ServiceSubjectLoaderImpl();
    }
}
