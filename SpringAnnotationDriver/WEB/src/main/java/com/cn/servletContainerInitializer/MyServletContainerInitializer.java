package com.cn.servletContainerInitializer;

import com.cn.Filter.UserFilter;
import com.cn.Listener.UserListener;
import com.cn.service.HelloService;
import com.cn.servlet.UserServlet;

import javax.servlet.*;
import javax.servlet.annotation.HandlesTypes;
import java.util.EnumSet;
import java.util.Set;

/**
 * Created by admin on 2018/7/12.
 */

//容器启动的时候会将@HandlesTypes指定的这个类型下面的子类（实现类，子接口等）传递过来

//传入感兴趣的类型:
@HandlesTypes(value = {HelloService.class})
public class MyServletContainerInitializer implements ServletContainerInitializer {

    /**
     * 应用启动的时候，会运行onStartup
     *
     * @param set            感兴趣类型的所有子类型（后代类型）
     * @param servletContext 代表当前Web应用的ServletContext；
     *                       一个Web应用对应一个ServletContext
     * @throws ServletException
     *
     * 1.使用ServletContext注册Web组件（Servlet,Filter,Listener）
     * 2.使用编码的方式，在项目启动的时候给ServletContext里面添加组件
     *       必须在项目启动的时候来添加
     *       1.ServletContainerInitializer能得到servletContext
     *       2.在ServletContextListener中能得到servletContext
     */
    @Override
    public void onStartup(Set<Class<?>> set, ServletContext servletContext) throws ServletException {

        System.out.println("感兴趣的类型");
        for (Class<?> claz : set) {
            System.out.println(claz);
        }


        //注册组件
        ServletRegistration.Dynamic userServlet = servletContext.addServlet("UserServlet", new UserServlet());
        //配置Servlet的映射信息
        userServlet.addMapping("/user");


        //注册Listener
        servletContext.addListener(UserListener.class);

        //注册Filter
        FilterRegistration.Dynamic userFilter = servletContext.addFilter("userFilter", UserFilter.class);
        //Filter的映射信息
        userFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST),true,"/*");

    }
}
