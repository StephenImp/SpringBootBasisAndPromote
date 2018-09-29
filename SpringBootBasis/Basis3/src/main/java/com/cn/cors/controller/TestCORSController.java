package com.cn.cors.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestCORSController
{

    @RequestMapping(value = "/cors")
    public String corsIndex(){
        return "this is cors info.";
    }
}
