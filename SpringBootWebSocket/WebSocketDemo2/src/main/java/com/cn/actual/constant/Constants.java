package com.cn.actual.constant;


/**
 * 
 ************************************************************
 * @类名 : Constants.java
 *
 * @DESCRIPTION : 常量类
 * @DATE : 2017年8月29日
 ************************************************************
 */
public class Constants {

    /*pwm token 存在redis上的名字*/
    public static final String PWM_TOKEN_NAME="PWM_TOKEN";
    
    /*pwm token 存在redis上的有效时间*/
    public static final Long PWM_TOKEN_EXPIRE=100*60L;//100分钟
    
    /*pwm token 存在redis上的有效时间*/
    public static final Long BOOK_REQUEST_INFO_EXPIRE=72*60*60L;//3天

    /*token有效期（小时）*/
    public static final int TOKEN_EXPIRES_HOUR = 72;
    
    /*请求头中的local-ip名称*/
    public static final String HEADER_LOCAL_IP = "Local-Ip";
    /*请求头中的Pwm-Token名称*/
    public static final String HEADER_PWM_TOKEN = "Pwm-Token";

    
    public static final String RESPONSE_STATUS = "200";// 返回状态
}
