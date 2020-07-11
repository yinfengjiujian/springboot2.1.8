package com.neusoft.study.springboot.config;

import com.neusoft.study.springboot.config.properties.GoEasyPrpoerties;
import io.goeasy.GoEasy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Title: com.neusoft.study.springboot.config</p>
 * <p>Company:爱尔眼科集团</p>
 * <p>Copyright:Copyright(c)</p>
 * User: Administrator
 * Date: 2020/7/11 11:08
 * Description: No Description
 */
@Configuration
public class GoeasyConfig {

    @Autowired
    private GoEasyPrpoerties goEasyPrpoerties;

    @Bean
    public GoEasy initoGoeasy() {
        return new GoEasy(goEasyPrpoerties.getHost(),goEasyPrpoerties.getAppKey());
    }
}
