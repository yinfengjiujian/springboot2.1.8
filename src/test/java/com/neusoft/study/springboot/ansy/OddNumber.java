package com.neusoft.study.springboot.ansy;

import java.util.concurrent.Callable;

/**
 * <p>Title: com.neusoft.study.springboot.ansy</p>
 * <p>Company:东软集团(neusoft)</p>
 * <p>Copyright:Copyright(c)</p>
 * User: Administrator
 * Date: 2019/10/23 21:22
 * Description: No Description
 */
public class OddNumber implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        Thread.sleep(30000);
        int result = 1 + 3 + 5 + 7 + 9;
        return result;
    }
}
