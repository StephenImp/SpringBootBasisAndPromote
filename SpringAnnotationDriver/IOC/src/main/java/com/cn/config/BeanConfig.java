package com.cn.config;

import com.cn.bean.PersonBean;
import com.cn.condition.LinuxCondition;
import com.cn.condition.WindowsCondition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * Created by admin on 2018/7/11.
 */
@Configuration
public class BeanConfig {
    //默认是单实例的
    /**
     * ConfigurableBeanFactory#SCOPE_PROTOTYPE
     * @see ConfigurableBeanFactory#SCOPE_SINGLETON
     * @see org.springframework.web.context.WebApplicationContext#SCOPE_REQUEST  request
     * @see org.springframework.web.context.WebApplicationContext#SCOPE_SESSION	 sesssion
     * @return\
     * @Scope:调整作用域
     * prototype：多实例的：ioc容器启动并不会去调用方法创建对象放在容器中。
     * 					每次获取的时候才会调用方法创建对象；
     * singleton：单实例的（默认值）：ioc容器启动会调用方法创建对象放到ioc容器中。
     * 			以后每次获取就是直接从容器（map.get()）中拿，
     * request：同一次请求创建一个实例
     * session：同一个session创建一个实例
     *
     * 懒加载：
     * 		单实例bean：默认在容器启动的时候创建对象；
     * 		懒加载：容器启动不创建对象。第一次使用(获取)Bean创建对象，并初始化；
     *
     */
	//@Scope("prototype")
    //@Lazy
    @Bean("person")
    public PersonBean person(){
        System.out.println("获取bean实例");
        return new PersonBean("AA",12);
    }

    /**
     * @Conditional({Condition}) ： 按照一定的条件进行判断，满足条件给容器中注册bean
     *
     * 如果系统是windows，给容器中注册("bill")
     * 如果是linux系统，给容器中注册("linus")
     */

    @Conditional(WindowsCondition.class)
    @Bean("bill")
    public PersonBean person01(){
        return new PersonBean("Bill Gates",62);
    }

    @Conditional(LinuxCondition.class)
    @Bean("linus")
    public PersonBean person02(){
        return new PersonBean("linus", 49);
    }
}
