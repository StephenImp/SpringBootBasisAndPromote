package com.cn.visits.controller;

import com.cn.visits.annotations.RequestTimes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by admin on 2018/6/11.
 */
@Controller
public class VisitsRedisDemoController {

    @RequestMapping(value = "test/redis/visit",method = RequestMethod.POST)
    @RequestTimes(count = 3, time = 60000)
    @ResponseBody
    public String testRedisVisit(@RequestBody Map<String,Object> user, HttpServletRequest request){

        System.out.println(request.getAttribute("ifOverTimes"));
        if (request.getAttribute("ifOverTimes").equals("false")) {
            System.out.println(user.get("username"));
            return "请求成功!!!";
        }
        System.out.println(user.get("username"));
        return "HTTP请求超出设定的限制!!!";
    }
}
