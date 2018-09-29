package com.cn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class TestSecurityController {

    /**
     * 在WebSecurityConfig中,配置了只有/login是可以直接访问的,
     * <p>
     * 当我们在没有登录的状态下访问/hello，会直接被安全框架重定向到登录页面，
     */


    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }

}
