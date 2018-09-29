package com.cn.TestIocDemo;

import com.cn.bean.Car;
import com.cn.config.MainConfigOfLifeCycle;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by admin on 2018/7/11.
 */
public class TestConfigOfLifeCycle {

    static AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfLifeCycle.class);

    public static void main(String[] args) {

        System.out.println("容器创建完成！！！");
        Car bean = applicationContext.getBean(Car.class);

        System.out.println(bean);

        //关闭容器
        applicationContext.close();

    }
}
