package com.cn.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/user")
public class IndexController {

    private final static Logger logger = LoggerFactory.getLogger(IndexController.class);

    /**
     * 初始化登录页面
     */
    @RequestMapping(value = "/login_view", method = RequestMethod.GET)
    public String login_view() {
        logger.info("初始化登录页面");
        return "login";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        logger.info("访问index方法");
        return "index";
    }
}
