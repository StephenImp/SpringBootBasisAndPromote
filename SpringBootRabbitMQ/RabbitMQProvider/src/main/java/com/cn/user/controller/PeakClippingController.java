package com.cn.user.controller;

import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CountDownLatch;

/**
 * Created by admin on 2018/8/21.
 */
public class PeakClippingController {

    RestTemplate template = new RestTemplate();

    /**
     * 请求购票地址
     */
    private final String url = "http://localhost:11000/user/findOne/2";
    private static final int user_num = 200;

    /**
     * 发枪器
     */
    private static CountDownLatch cdl = new CountDownLatch(user_num);

    @Test
    public void testInvokeRemote() throws Exception{
        for (int i = 0;i<user_num;i++) {
            new Thread(new TicketRequest()).start();
            /**
             * 线程全部启动完成之后，同时高并发。
             */
            cdl.countDown();
        }

        Thread.sleep(3000);

        String result = template.getForEntity(url,String.class).getBody();
        System.out.println(result);
    }

    public class TicketRequest implements Runnable{
        @Override
        public void run() {
            try {
                cdl.await();//之前的所有线程处于等待状态
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
