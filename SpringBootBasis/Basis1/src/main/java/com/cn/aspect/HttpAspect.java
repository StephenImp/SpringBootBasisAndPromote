package com.cn.aspect;

import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by admin on 2017/11/1.
 */
@Aspect
@Component
public class HttpAspect {

    private final static Logger logger  = LoggerFactory.getLogger(HttpAspect.class);


    @Pointcut("execution(public * com.cn.controller.GirlController.*(..))")
    public void log(){
        System.out.println("HttpAspect.log");
    }


    @Before("log()")
    public void doBefore(){

        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request =  attributes.getRequest();

        //url
        logger.info("url = {}",request.getRequestURI());

        //method
        logger.info("method = {}",request.getMethod());

        //ip
        logger.info("ip= {}",request.getRemoteAddr());

        //类方法
        //joinPoint?
        //logger.info("Class_method = {}", joinPoint.getSignature().);

        //参数

    }

    @After("log()")
    public void doAfter(){
        System.out.println("HttpAspect.doAfter");
        logger.info("啦啦");
    }

    @AfterReturning(returning = "object",pointcut = "log()")
    public void  doAfterRetruning(Object object){
        logger.info("response={}",object.toString());

    }
}
