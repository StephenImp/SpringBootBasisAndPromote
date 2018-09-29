package com.cn.TestIocDemo;

import com.cn.bean.Blue;
import com.cn.bean.PersonBean;
import com.cn.config.BeanConfig2;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * Created by admin on 2018/7/11.
 */
public class TestIoc2 {

    static AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(BeanConfig2.class);

    public static void main(String[] args) {
        printBeans(applicationContext);

        Blue bean = applicationContext.getBean(Blue.class);
        System.out.println(bean);

        //工厂Bean获取的是调用getObject创建的对象
        Object bean2 = applicationContext.getBean("colorFactoryBean");
        Object bean3 = applicationContext.getBean("colorFactoryBean");
        System.out.println("bean的类型："+bean2.getClass());
        System.out.println(bean2 == bean3);

        //加 & 前缀，Spring容器就知道是FactoryBean本身
        Object bean4 = applicationContext.getBean("&colorFactoryBean");
        System.out.println("@@@@@@@@@"+bean4.getClass());
    }

    private static  void printBeans(AnnotationConfigApplicationContext applicationContext){
        String[] definitionNames = applicationContext.getBeanDefinitionNames();
        for (String name : definitionNames) {
            System.out.println(name);
        }
    }
}
