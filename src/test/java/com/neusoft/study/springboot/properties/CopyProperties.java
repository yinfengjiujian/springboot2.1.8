package com.neusoft.study.springboot.properties;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.neusoft.study.springboot.utils.BeanCopyUtils;
import lombok.Data;
import org.junit.Test;

import javax.validation.constraints.NotBlank;

/**
 * <p>Title: com.neusoft.study.springboot.properties</p>
 * <p>Company:东软集团(neusoft)</p>
 * <p>Copyright:Copyright(c)</p>
 * User: Administrator
 * Date: 2019/10/27 9:47
 * Description: No Description
 */
public class CopyProperties {

    @Test
    public void testCopy () {
        Car car = new Car();
        car.setAge(25);
        car.setName("哈勒毛");
        car.setId(IdWorker.getId());

        Study study = new Study();

        BeanCopyUtils.copyProperties(car,study);

        System.out.println(study.toString());

    }

    @Data
    class Car {
        @NotBlank
        private Integer age;
        private String name;
        private Long id;
    }

    @Data
    class Study {
        private Integer age;
        private String name;
    }


}
