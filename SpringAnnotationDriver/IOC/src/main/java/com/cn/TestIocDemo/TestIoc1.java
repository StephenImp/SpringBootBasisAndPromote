package com.cn.TestIocDemo;

import com.cn.bean.PersonBean;
import com.cn.config.BeanConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * Created by admin on 2018/7/11.
 * IOC走源码，底层是hashMap存储
 */
public class TestIoc1 {


    static AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(BeanConfig.class);

    public static void main(String[] args) {

        /**
         *  System.out.println("Ioc容器创建完成");
            PersonBean personBean = applicationContext.getBean(PersonBean.class);
         */

        String[] namesForType = applicationContext.getBeanNamesForType(PersonBean.class);

        ConfigurableEnvironment environment = applicationContext.getEnvironment();

        String property = environment.getProperty("os.name");
        //动态获取环境变量的值；Windows 7
        System.out.println(property);

        for (String name : namesForType) {
            System.out.println(name);
        }
    }

}
