package com.cn.eventandlistener.listener.annotation;

import com.cn.eventandlistener.event.UserRegisterEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

//@Component
public class AnnotationRegisterUserEmailListener {
    /**
     * 发送邮件监听实现
     *
     * @param userRegisterEvent 用户注册事件
     */
    //@EventListener
    public void sendMail(UserRegisterEvent userRegisterEvent) {
        System.out.println("用户注册成功，发送邮件。");
    }
}
