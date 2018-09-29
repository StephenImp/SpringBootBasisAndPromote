package com.cn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.session.web.http.HttpSessionStrategy;

/**
 * Created by admin on 2018/6/27.
 * 配置类开启Redis Http Session
 *
 * @EnableRedisHttpSession
 * 创建了一个名为springSessionRepositoryFilter的Spring Bean，
 * 该Bean实现了Filter接口。该filter负责通过 Spring Session 替换HttpSession从哪里返回。
 * 这里Spring Session是通过 redis 返回。
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 86400*30)
public class HttpSessionConfig {

    /**
     * 两种方式：、
     * 1.从请求头中获取
     * 2.从cookie中
     * @return
     */

    @Bean
    public HttpSessionStrategy httpSessionStrategy() {
        return new HeaderHttpSessionStrategy();
    }

    /**
     *
     *
     * 类中的方法 httpSessionStrategy()，用来定义Spring Session的 HttpSession 集成使用HTTP的头来取代使用 cookie 传送当前session信息。
     * 如果使用下面的代码，则是使用cookie来传送 session 信息。
     *
        @Bean
        public HttpSessionStrategy httpSessionStrategy() {
            return new CookieHttpSessionStrategy();
        }
     */
}
