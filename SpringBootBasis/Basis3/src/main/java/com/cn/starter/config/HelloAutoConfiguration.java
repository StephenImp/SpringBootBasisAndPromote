package com.cn.starter.config;

import com.cn.starter.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @EnableConfigurationProperties：这是一个开启使用配置参数的注解，value值就是我们配置实体参数映射的ClassType，将配置实体作为配置来源。
 *
 *   @ConditionalOnBean：当SpringIoc容器内存在指定Bean的条件
     @ConditionalOnClass：当SpringIoc容器内存在指定Class的条件
     @ConditionalOnExpression：基于SpEL表达式作为判断条件
     @ConditionalOnJava：基于JVM版本作为判断条件
     @ConditionalOnJndi：在JNDI存在时查找指定的位置
     @ConditionalOnMissingBean：当SpringIoc容器内不存在指定Bean的条件
     @ConditionalOnMissingClass：当SpringIoc容器内不存在指定Class的条件
     @ConditionalOnNotWebApplication：当前项目不是Web项目的条件
     @ConditionalOnProperty：指定的属性是否有指定的值
     @ConditionalOnResource：类路径是否有指定的值
     @ConditionalOnSingleCandidate：当指定Bean在SpringIoc容器内只有一个，或者虽然有多个但是指定首选的Bean
     @ConditionalOnWebApplication：当前项目是Web项目的条件
 */

@Configuration//开启配置
@EnableConfigurationProperties(HelloProperties.class)//开启使用映射实体对象
@ConditionalOnClass(HelloService.class)//存在HelloService时初始化该配置类
@ConditionalOnProperty//存在对应配置信息时初始化该配置类
        (
                prefix = "hello",//存在配置前缀hello
                value = "enabled",//开启
                matchIfMissing = true//缺失检查
        )
public class HelloAutoConfiguration
{

    //application.properties配置文件映射前缀实体对象
    @Autowired
    private HelloProperties helloProperties;

    /**
     * 根据条件判断不存在HelloService时初始化新bean到SpringIoc
     * @return
     */
    @Bean//创建HelloService实体bean
    @ConditionalOnMissingBean(HelloService.class)//缺失HelloService实体bean时，初始化HelloService并添加到SpringIoc
    public HelloService helloService()
    {
        System.out.println(">>>The HelloService Not Found，Execute Create New Bean.");
        HelloService helloService = new HelloService();
        helloService.setMsg(helloProperties.getMsg());//设置消息内容
        helloService.setShow(helloProperties.isShow());//设置是否显示
        return helloService;
    }
}
