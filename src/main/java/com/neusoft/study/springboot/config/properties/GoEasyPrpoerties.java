package com.neusoft.study.springboot.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>Title: com.neusoft.study.springboot.config.properties</p>
 * <p>Company:爱尔眼科集团</p>
 * <p>Copyright:Copyright(c)</p>
 * User: Administrator
 * Date: 2020/7/11 11:11
 * Description: No Description
 */
@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "goeasy")
public class GoEasyPrpoerties {

    private String host;

    private String appKey;
}
