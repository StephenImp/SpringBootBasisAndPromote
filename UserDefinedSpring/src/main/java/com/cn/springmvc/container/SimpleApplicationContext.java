package com.cn.springmvc.container;

import com.cn.springmvc.annotation.*;
import com.cn.springmvc.utils.PathUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2018/8/8.
 *
 */
public class SimpleApplicationContext {

    public static Map<String, Object> beans = new HashMap<>();

    public static Map<String, Object> handleMap = new HashMap<>();

    /**
     * 根据扫描出来的类，进行实例化
     */
    public void initBean() {
        if (PathUtils.classNames.size() <= 0) {
            System.out.println("包扫描失败。。。");
            return;
        }

        //list的所有class类，
        for (String className : PathUtils.classNames) {
            String cn = className.replace(".class", "");

            /**
             * 为每个class文件创建类的实例，并存入map中。
             */
            try {
                Class<?> clazz = Class.forName(cn);

                try {
                    //如果指定类型的注解存在于此类上,返回true,否则返回false
                    if (clazz.isAnnotationPresent(EnjoyController.class)) {
                        Object instance = clazz.newInstance();//创建控制类。
                        //获得该类上指定的注解
                        EnjoyRequestMaping requestMaping = clazz.getAnnotation(EnjoyRequestMaping.class);
                        String rmvalue = requestMaping.value();//拿到请求路径
                        beans.put(rmvalue, instance);
                    } else if (clazz.isAnnotationPresent(EnjoyService.class)) {
                        EnjoyService enjoyService = clazz.getAnnotation(EnjoyService.class);
                        Object instance = clazz.newInstance();
                        beans.put(enjoyService.value(), instance);
                    } else {
                        continue;
                    }
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 给类中的属性赋值。
     */
    public void giveValue() {
        if (beans.entrySet().size() <= 0) {
            System.out.println("没有一个实例化的类。。。");
        }

        /**
         * 遍历map中的所有的实例化的类
         */
        for (Map.Entry<String, Object> entry : beans.entrySet()) {
            Object instance = entry.getValue();
            Class<?> clazz = instance.getClass();

            //获取每个实例化类的属性
            Field[] fileds = clazz.getDeclaredFields();

            /**
             * 判断哪些变量用到了注解
             */
            if (clazz.isAnnotationPresent(EnjoyController.class)) {
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    if (field.isAnnotationPresent(EnjoyAutowired.class)) {

                        //如果是EnjoyAutowired注解。
                        EnjoyAutowired auto = field.getAnnotation(EnjoyAutowired.class);

                        //根据EnjoyAutowired中的名字
                        String key = auto.value();//eg:demoServiceImpl
                        field.setAccessible(true);//因为属性是私有的，所以要设置权限。
                        try {
                            /**
                             * 根据EnjoyAutowired中的名字，把ioc中的实例与名字注入到属性中。
                             */
                            field.set(instance, beans.get(key));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    } else {
                        continue;
                    }
                }
            }
        }

    }

    /**
     * 获取请求地址
     */
    public void obtainUrlMapping() {

        if (beans.entrySet().size() <= 0) {
            System.out.println("没有类进行实例化。。。");
            return;
        }

        for (Map.Entry<String, Object> entry : beans.entrySet()) {
            Object instance = entry.getValue();
            Class<?> clazz = instance.getClass();
            if (clazz.isAnnotationPresent(EnjoyController.class)) {
                EnjoyRequestMaping requestMaping = clazz.getAnnotation(EnjoyRequestMaping.class);
                String classPath = requestMaping.value();//  demo

                Method[] methods = clazz.getMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(EnjoyRequestMaping.class)) {
                        EnjoyRequestMaping methodMaping = method.getAnnotation(EnjoyRequestMaping.class);
                        String methodPath = methodMaping.value();//  test
                        handleMap.put(classPath + methodPath, method);
                    } else {
                        continue;
                    }
                }
            }
        }

    }

    /**
     * 获取方法的请求参数
     * spring的底层是用策略模式写的。
     */
    public  Object[] obtainParam(HttpServletRequest request, HttpServletResponse response, Method method) {


        //拿到当前执行的方法的参数类型
        Class<?>[] parameterTypes = method.getParameterTypes();

        //根据参数的个数，new一个参数的数组，将方法里的所有参数赋值到args来
        Object[] args = new Object[parameterTypes.length];

        int args_i = 0;
        int index = 0;

        for (Class<?> paramClazz : parameterTypes) {

            //自身类.class.isAssignableFrom(自身类或子类.class)  返回true
            if (ServletRequest.class.isAssignableFrom(paramClazz)) {
                args[args_i++] = request;
            }

            if (ServletResponse.class.isAssignableFrom(paramClazz)) {
                args[args_i++] = response;
            }

            //从0-3判断没有RequestParam注解，很明显paramClazz为0和1时，不是，
            //当为2和3时为@RequestParam，需要解析
            //[@com.cn.annotation.EnjoinRequestParam(value = name)]
            // method.getParameterAnnotations()[index] 用法见下面的注释
            Annotation[] paramAns = method.getParameterAnnotations()[index];

            if (paramAns.length > 0) {
                for (Annotation paramAn : paramAns) {
                    if (EnjoyRequestParam.class.isAssignableFrom(paramAn.getClass())) {
                        EnjoyRequestParam rp = (EnjoyRequestParam) paramAn;
                        //找到注解中的name和age
                        args[args_i++] = request.getParameter(rp.value());
                    }
                }
            }
            index++;
        }
        return args;
    }

    /**
     * Annotation[][] annos = method.getParameterAnnotations();
     得到的结果是一个二维数组.
     那么这个二维数组是怎么排列组合的呢?
     首先举个例子:
     @RedisScan public void save(@RedisSave()int id,@RedisSave()String name){}
     第一个参数下表为0,第二个为1
     也就是说:
     annos[0][0] = RedisSave
     annos[1][0] = RedisSave
     也就是说,二维数组是包含多个仅有一个值的数组.
     */
}
