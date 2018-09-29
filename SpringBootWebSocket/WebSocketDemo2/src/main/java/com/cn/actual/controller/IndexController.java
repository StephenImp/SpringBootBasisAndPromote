package com.cn.actual.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 这里是前往webSocket的入口，因为用的是templates模板引擎，不能直接去请求
 */

@Controller
public class IndexController {

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index() {
        return "websocket";
    }
}
