package com.cn.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MVCConfig extends WebMvcConfigurerAdapter

        /**
         *   下面我们添加一个名叫MVCConfig配置类继承WebMvcConfigurerAdapter类，
         *   重写addViewControllers()方法添加路径访问，
         *   可以通过Get形式的/login访问到我们的login.jsp
         */
{
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/main").setViewName("main");
    }
}
