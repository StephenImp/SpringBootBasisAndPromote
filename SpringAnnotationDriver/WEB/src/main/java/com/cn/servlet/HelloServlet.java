package com.cn.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by admin on 2018/7/12.
 */
@WebServlet(value = "/hello")
public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println(Thread.currentThread()+"start...");

        try {
            sayHello();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread()+"end...");
    }

    public void sayHello() throws Exception {

        System.out.println(Thread.currentThread()+"ing...");
        Thread.sleep(3000);

        System.out.println("Hello");
    }
}
