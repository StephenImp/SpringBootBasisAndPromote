package com.cn.controller;

import com.cn.config.SpringBootProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by admin on 2017/10/30.
 * @author  wpw
 */
@RestController
@RequestMapping("/demo")
public class HelloController {

   @Autowired
   private SpringBootProperties springBootProperties;

    @RequestMapping(value = {"/hello/{id}","hi/{id}"},method = RequestMethod.GET)
    public String demo1(@PathVariable("id") Integer id){

       // return springBootProperties.getCupSize();

        //测试git
        //提交git
        return  "id :"+ id;
    }


    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String demo2(@RequestParam(value = "id",required = false,defaultValue = "0") Integer myId){
        return  "id :"+ myId;
    }

    @GetMapping(value = "/say")
    public String demo3(@RequestParam(value = "id",required = false,defaultValue = "0") Integer myId){
        return  "id :"+ myId;
    }
}
