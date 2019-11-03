package com.neusoft.study.springboot.ansy;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * <p>Title: com.neusoft.study.springboot.ansy</p>
 * <p>Company:东软集团(neusoft)</p>
 * <p>Copyright:Copyright(c)</p>
 * User: Administrator
 * Date: 2019/10/23 21:23
 * Description: No Description
 */
public class FutureTest {

    @Test
    public void testFulure() {
        ExecutorService executor = Executors.newCachedThreadPool();
        OddNumber oddNumber = new OddNumber();
        Future<Integer> future = executor.submit(oddNumber);
        long startTime = System.currentTimeMillis();
        int evenNumber = 2 + 4 + 6 + 8 + 10;
        try {
            Thread.sleep(1000);
            System.out.println("0.开始了："+ (System.currentTimeMillis()-startTime) +"秒");
            int oddNumberResult = future.get();//这时间会被阻塞
            System.out.println("1+2+...+9+10="+(evenNumber+oddNumberResult));
            System.out.println("1.开始了："+ (System.currentTimeMillis()-startTime) +"秒");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
