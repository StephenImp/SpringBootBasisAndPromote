package com.cn.config;

import com.cn.controller.servlet.TestServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServletConfiguration {


    /**
     * 这里的/test 是servlet的请求路径
     */
    @Bean
    public ServletRegistrationBean servletRegistrationBean()
    {
        return new ServletRegistrationBean(new TestServlet(),"/test");
    }

}
