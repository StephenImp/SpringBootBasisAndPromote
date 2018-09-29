package com.cn.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * ========================
 * Created with IntelliJ IDEA.
 * User：wpw
 * Date：2017/4/11
 * Time：20:55
 * ========================
 */
@Configuration
public class StaticResourcesConfiguration extends WebMvcConfigurerAdapter {
    //自定义静态资源文件路径
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/testStatic/resources/imgs/**").addResourceLocations("classpath:/static/");
    }
}
