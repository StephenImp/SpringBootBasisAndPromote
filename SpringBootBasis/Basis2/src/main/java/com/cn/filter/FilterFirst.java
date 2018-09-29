package com.cn.filter;

import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * Created by admin on 2018/7/13.
 */
@Order(1)
//重点
@WebFilter(filterName = "FilterFirst", urlPatterns = "/*")
public class FilterFirst implements Filter {

    /**
     * Spring启动时就加载了init方法了
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("FilterFirst....init");

    }

    /**
     * 有请求的话，就会调用这个方法
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        System.out.println("FilterFirst....doFilter");
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {
        System.out.println("FilterFirst....destroy");
    }

}
