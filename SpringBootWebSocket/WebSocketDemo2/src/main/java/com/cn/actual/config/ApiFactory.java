package com.cn.actual.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 
 ************************************************************
 * @类名 : UmApiFactory.java
 *
 * @DESCRIPTION : 简单工厂，用于模块之间解耦合
 * @AUTHOR :  cgj
 * @DATE :  2017年11月22日
 ************************************************************
 */
public class ApiFactory implements ApplicationContextAware {

    private static ApplicationContext applicationContext;
    
    public ApiFactory() {
        // TODO Auto-generated constructor stub
    }
    
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // TODO Auto-generated method stub
        if(ApiFactory.applicationContext == null) {
            ApiFactory.applicationContext = applicationContext;
        }
    }
    
    //获取applicationContext
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
    
    //通过name获取 Bean.
    public static Object getBean(String name){
        return getApplicationContext().getBean(name);
    }

    //通过class获取Bean.
    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }

    //通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name,Class<T> clazz){
        return getApplicationContext().getBean(name, clazz);
    }

}
