package com.cn.eventandlistener.controller;

import com.cn.eventandlistener.bean.UserBean;
import com.cn.eventandlistener.service.UserEventListenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserEventListenerController {
    //用户业务逻辑实现
    @Autowired
    private UserEventListenerService userEventListenerService;

    /**
     * 注册控制方法
     *
     * @param user 用户对象
     * @return
     */
    @RequestMapping(value = "/register")
    public String register(UserBean user) {
        //调用注册业务逻辑
        userEventListenerService.register(user);
        return "注册成功.";
    }
}
