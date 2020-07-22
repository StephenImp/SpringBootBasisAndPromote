package com.cn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis")
public class TestRedisController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(value = {
            "/test"
    }, produces = {
            "application/json;charset=UTF-8"
    }, method = RequestMethod.GET)
    public boolean getAllUsers() {
        stringRedisTemplate.opsForValue().set("aaa", "111");
        System.out.println("11");
        return true;
    }
}
