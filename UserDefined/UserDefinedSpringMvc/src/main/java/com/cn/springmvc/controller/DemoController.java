package com.cn.springmvc.controller;

import com.cn.springmvc.annotation.EnjoyAutowired;
import com.cn.springmvc.annotation.EnjoyController;
import com.cn.springmvc.annotation.EnjoyRequestMaping;
import com.cn.springmvc.annotation.EnjoyRequestParam;
import com.cn.springmvc.service.DemoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Created by admin on 2018/8/7.
 */
@EnjoyController
@EnjoyRequestMaping("/demo")
public class DemoController {

    @EnjoyAutowired("demoServiceImpl")
    private DemoService demoService;

    @EnjoyRequestMaping("/query")
    public void query(HttpServletRequest request, HttpServletResponse response,
                      @EnjoyRequestParam("name") String name, @EnjoyRequestParam("age") String age) {

        try {

            PrintWriter pw = response.getWriter();
            String result = demoService.query(name, age);
            pw.write(result);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
