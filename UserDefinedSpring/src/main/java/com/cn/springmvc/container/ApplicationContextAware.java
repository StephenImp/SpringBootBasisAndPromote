package com.cn.springmvc.container;


import com.cn.springmvc.exception.BeansException;

/**
 * Created by admin on 2018/8/8.
 */
public interface ApplicationContextAware{

    void setApplicationContext(SimpleApplicationContext simpleApplicationContext) throws BeansException;
}
