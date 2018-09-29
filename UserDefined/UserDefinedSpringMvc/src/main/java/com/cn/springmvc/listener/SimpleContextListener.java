package com.cn.springmvc.listener;

import com.cn.springmvc.container.SimpleApplicationContext;
import com.cn.springmvc.utils.PathUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by admin on 2018/8/8.
 */
public class SimpleContextListener implements ServletContextListener{


    //服务器启动或者程序被加载时，调用方法
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        //SimpleApplicationContext  simpleApplicationContext = new SimpleApplicationContext();

        Class<SimpleApplicationContext> clazz = SimpleApplicationContext.class;

        SimpleApplicationContext simpleApplicationContext = null;
        try {
            simpleApplicationContext = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        PathUtils p = new PathUtils();

        //扫描所有的bean---扫描所有的class文件。
        p.scanPackage("com.cn.demoLearn");//写到preperties

        simpleApplicationContext.initBean();//将通过controller和service标注的实体类全部通过反射获取实例。

        simpleApplicationContext.giveValue();// 给controller类中的属性赋值。

        simpleApplicationContext.obtainUrlMapping();//  获取请求地址    demo/query

    }

    //服务器卸载该web应用程序时，调用方法
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }


}
