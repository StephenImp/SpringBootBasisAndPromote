package com.cn.user.controller;

import com.cn.user.entity.UserEntity;
import com.cn.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/user")
public class UserController
{
    /**
     * 用户业务逻辑
     */
    @Autowired
    private UserService userService;

    /**
     * 保存用户基本信息
     * @param userEntity
     * @return
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public UserEntity save(UserEntity userEntity) throws Exception
    {
        userService.save(userEntity);
        return userEntity;
    }

    @RequestMapping(value = "/findOne/{id}",method = RequestMethod.GET)
    public UserEntity findOne(@PathVariable("id") Long id){
        return userService.findOne(id);
    }
}
