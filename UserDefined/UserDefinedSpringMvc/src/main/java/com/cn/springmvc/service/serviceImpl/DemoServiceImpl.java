package com.cn.springmvc.service.serviceImpl;

import com.cn.springmvc.annotation.EnjoyService;
import com.cn.springmvc.service.DemoService;

/**
 * Created by admin on 2018/8/7.
 */
@EnjoyService("demoServiceImpl")
public class DemoServiceImpl implements DemoService {

    @Override
    public String query(String name, String age) {
        return "name--->:"+name+";"+"   age--->:"+age;
    }
}
