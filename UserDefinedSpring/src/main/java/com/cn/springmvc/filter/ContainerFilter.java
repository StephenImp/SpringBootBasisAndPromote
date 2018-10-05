package com.cn.springmvc.filter;

import com.cn.springmvc.container.ApplicationContextAware;
import com.cn.springmvc.container.SimpleApplicationContext;
import com.cn.springmvc.exception.BeansException;

/**
 * Created by admin on 2018/8/10.
 */
public class ContainerFilter implements ApplicationContextAware {

    private SimpleApplicationContext simpleApplicationContext;

    @Override
    public void setApplicationContext(SimpleApplicationContext simpleApplicationContext) throws BeansException {
        this.simpleApplicationContext = simpleApplicationContext;
    }
}
