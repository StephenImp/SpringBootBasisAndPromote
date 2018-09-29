package com.cn.Listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Created by admin on 2018/7/12.
 *
 * 繼承XXXListener接口 ，使用@WebListener還是要指定去實現某個Listener
 *
 * 用来监听项目的启动或者停止
 */
//@WebListener
public class UserListener implements ServletContextListener{

    /**
     * 监听ServletContext的启动
     * @param servletContextEvent
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        servletContextEvent.getServletContext();

        System.out.println("ServletContextListener...contextInitialized...");

    }

    /**
     * 监听ServletContext的停止
     * @param servletContextEvent
     */
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

        System.out.println("ServletContextListener...contextDestroyed...");

    }
}
