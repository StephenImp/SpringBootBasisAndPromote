package com.cn.springmvc.servlet;

import com.cn.springmvc.container.ApplicationContextAware;
import com.cn.springmvc.controller.DemoController;
import com.cn.springmvc.container.SimpleApplicationContext;
import com.cn.springmvc.exception.BeansException;
import com.cn.springmvc.filter.ContainerFilter;
import com.cn.springmvc.utils.PathUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/8/7.
 */
public class EnjoyDispatcherServlet extends HttpServlet implements ApplicationContextAware {

    private static SimpleApplicationContext simpleApplicationContext;

    private static final String APPLICATIONCONTEXT_METHODNAME_ = "setApplicationContext";
    private static final String APPLICATION_CONTEXT__AWARE = "ApplicationContextAware";


    @Override
    public void init(ServletConfig config) throws ServletException {

        //给每个继承了ApplicationContextAware接口的类赋予容器
        this.obtainContainer();
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //获取请求路径
        String uri = req.getRequestURI();//eg :/demo/query
        String context = req.getContextPath(); //context--->""
        String path = uri.replace(context, "");// path:  /demo/query

        Method method = (Method) SimpleApplicationContext.handleMap.get(path);

        //根据之前在容器中存的key(这里的key是controller的请求路径)
        Object object = SimpleApplicationContext.beans.get("/" + path.split("/")[1]);
        DemoController instance = (DemoController) object;

        Object[] args = simpleApplicationContext.obtainParam(req, resp, method);//获取请求参数
        try {
            method.invoke(instance, args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setApplicationContext(SimpleApplicationContext simpleContext) throws BeansException {
        simpleApplicationContext = simpleContext;
    }

    /**
     * 给每个继承了ApplicationContextAware接口的类赋予容器
     */
    private  void obtainContainer(){
        //获取容器
        SimpleApplicationContext s = new SimpleApplicationContext();

        List<String> names = new ArrayList<>();

        /**
         * 从扫描的class文件中，找到继承了ApplicationContextAware接口的类。
         */
        for(String classPath: PathUtils.classNames){
            String cn = classPath.replace(".class", "");

            try {
                Class<?> clazz = Class.forName(cn);
                //是否继承了ApplicationContextAware接口
                boolean flag = ApplicationContextAware.class.isAssignableFrom(clazz);

                if (!flag){
                    continue;
                }

                //从效果上来看---》获取接口的实现类
                Class<?>[] interfaces = clazz.getInterfaces();

                for (Class<?> inte : interfaces) {//打印

                    String name = inte.getName();

                    String[] split = name.split("\\.");

                    names.add(split[split.length-1]);
                }

                if(names.contains(APPLICATION_CONTEXT__AWARE)){
                    //利用反射，调用setApplicationContext方法.
                    Method setApplicationContextMethod = clazz.getDeclaredMethod(APPLICATIONCONTEXT_METHODNAME_, SimpleApplicationContext.class);
                    setApplicationContextMethod.setAccessible(true);
                    setApplicationContextMethod.invoke(clazz.newInstance(), s);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {

        Class<ContainerFilter> containerFilterClass = ContainerFilter.class;
        Class<?>[] interfaces = containerFilterClass.getInterfaces();

        List<String> names = new ArrayList<>();

        for (Class<?> inte : interfaces) {//打印

            String name = inte.getName();

            String[] split = name.split("\\.");

            System.out.println(split[split.length-1]);

            //System.out.println("ContainerFilter实现接口："+inte.getName());
        }

    }

}
