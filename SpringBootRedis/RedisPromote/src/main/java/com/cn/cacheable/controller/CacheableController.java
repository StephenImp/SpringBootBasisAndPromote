package com.cn.cacheable.controller;

import com.cn.cacheable.entity.UserEntity;
import com.cn.cacheable.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Created by admin on 2018/6/5.
 */
@Controller
public class CacheableController {

    @Autowired
    private UserService userService;


    /**
     * 查询全部用户
     * @Cacheable
     * cacheNames 就是 value :表示这个Cacheable的名称
     */
    @RequestMapping(value = "testCacheable/findall",method = RequestMethod.POST)
    @ResponseBody
    public void  testCacheableFindAll(){

        List<UserEntity>  user = userService.findAll();
        System.out.println(user);
    }

    /**
     * 查询用户详情
     * 根据userId进行缓存
     * 并且年龄大于20的数据不进行缓存
     * #result?.userAge，?代表返回值为null时不做判断处理
     *
     * cacheNames+userId  为存到redis中的键
     *
     */
    @RequestMapping(value = "testCacheable/detail/{id}",method = RequestMethod.POST)
    @ResponseBody
    public void  testCacheableDetail(@PathVariable("id") Integer id){

        Optional<UserEntity> user = userService.detail(id);
        System.out.println(user);
    }

    /**
     * 删除用户
     * 配置@CacheEvict后会根据cacheName以及key删除redis内对应的缓存
     */
    @RequestMapping(value = "testCacheable/delete/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public void  testCacheableDelete(@PathVariable("id") Integer id){

       userService.delete(id);
       System.out.println("true");
    }


    /**
     *  一次清空多个缓存
     */
    @RequestMapping(value = "testCacheable/deleteTwo/{firstId}/{secondId}",method = RequestMethod.DELETE)
    @ResponseBody
    public void  testCacheableDeleteTwo(@PathVariable("firstId") Integer firstId,
                                        @PathVariable("secondId") Integer secondId){

        userService.deleteTwo(firstId,secondId);
        System.out.println("true");
    }

    /**
     * 更新用户基本信息
     * 根据参数进行更新用户信息
     * 配置@CachePut后会将方法返回值重新根据配置的cacheName以及key设置到redis内
     */
    @RequestMapping(value = "testCacheable/update/{userId}/{name}",method = RequestMethod.PUT)
    @ResponseBody
    public void  testCacheableUpdate(@PathVariable("userId") Integer userId,
                                        @PathVariable("name") String name){

        userService.update(userId,name);
        System.out.println("true");
    }

}
