package com.cn.servlet;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by admin on 2018/7/12.
 *
 * 默認情況下，tomcat內部會有一個線程池來管理
 *
 * 现在可以肯定，一个线程由一个方法启动，一个线程里面可以有多个方法，并不是每个方法都是一个线程。
 *
 * http请求，到业务处理，再到响应的过程，是在一个线程里面的。
   对tomcat来说，每一个进来的请求(request)都需要一个线程，直到该请求结束。
   tomcat会维护一个线程池，每一个http请求，会从线程池中取出一个空闲线程。
 *
 */
@WebServlet(value = "/async",asyncSupported = true)
public class HelloAsyncServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //1.支持異步處理 asyncSupported = true
        //2.開啟異步模式

        System.out.println("主線程開始。。。"+Thread.currentThread());

        AsyncContext startAsync = req.startAsync();

        //3.业务逻辑进行异步处理,开始异步处理
        startAsync.start(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("副線程結束。。。"+Thread.currentThread());
                    sayHello();
                    //异步处理请求完毕
                    startAsync.complete();
                    //获取异步上下文
                    AsyncContext asyncContext = req.getAsyncContext();
                    //获取异步响应
                    ServletResponse response = asyncContext.getResponse();
                    response.getWriter().write("hello async...");
                    System.out.println("副線程結束。。。"+Thread.currentThread());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println("主線程結束。。。"+Thread.currentThread());

    }

    public void sayHello() throws Exception {

        System.out.println(Thread.currentThread()+"ing...");
        Thread.sleep(3000);

        System.out.println("Hello");
    }
}
