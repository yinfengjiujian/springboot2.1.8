package com.neusoft.study.springboot;

import com.google.common.collect.Lists;
import com.neusoft.study.springboot.ansy.UserInfo;
import com.neusoft.study.springboot.ansy.UserService;
import org.junit.Test;
import org.springframework.util.StopWatch;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * <p>Title: com.neusoft.study.springboot</p>
 * <p>Company:东软集团(neusoft)</p>
 * <p>Copyright:Copyright(c)</p>
 * User: Administrator
 * Date: 2019/10/23 20:52
 * Description: No Description
 */
public class CompletableFutureTest {

    @Test
    public void testCompletable() {
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        long start = System.currentTimeMillis();
        CompletableFuture<String> futureA = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "商品详情";
        }, executorService);

        CompletableFuture<String> futureB = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "卖家信息";
        }, executorService);

        CompletableFuture<String> futureC = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "库存信息";
        }, executorService);

        CompletableFuture<String> futureD = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "订单信息";
        }, executorService);

        CompletableFuture<Void> allFuture = CompletableFuture.allOf(futureA, futureB, futureC, futureD);
        allFuture.join();

        System.out.println(futureA.join() + futureB.join() + futureC.join() + futureD.join());
        System.out.println("总耗时:" + (System.currentTimeMillis() - start));

    }


    /**
     * 异步多线程处理方式，自定义线程池进行处理
     */
    @Test
    public void testServiceCompletable() {
        Executor executor = Executors.newFixedThreadPool(20);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<String> userIds = Lists.newArrayList();
        userIds.add("123");
        userIds.add("124");
        userIds.add("125");
        userIds.add("126");
        List<CompletableFuture<UserInfo>> userInfoList = userIds.stream().map(s -> {
            return CompletableFuture.supplyAsync(() -> {
                UserService userService = new UserService();
                UserInfo userInfo = null;
                try {
                    userInfo = userService.queryUserInfoById(s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return userInfo;
            }, executor);
        }).collect(Collectors.toList());

        List<UserInfo> userInfoList1 = userInfoList.stream().map(completableFuture -> {
            return completableFuture.join();
        }).collect(Collectors.toList());

        for (UserInfo u : userInfoList1) {
            System.out.println(u.getNickName() + "--" + u.getUserId() + "--" + u.getUserName());
        }
        stopWatch.stop();
        long t = stopWatch.getTotalTimeMillis();
        System.out.println(t);
    }
}
