package com.cn.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloWorldController {

    /**
     * 在HelloWorldController内只添加一个字符串的输出，这个控制器我们开放，让SpringSecurity不去管理
     */

    @RequestMapping(method = RequestMethod.GET)
    public String sayHello() {
        return "Hello User!";
    }

}
