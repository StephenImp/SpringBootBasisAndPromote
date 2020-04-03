package com.cn.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Created by admin on 2018/6/7.
 *
 * @EnableCaching
 * spring会创建一个切面(aspect),并触发Spring缓存注解的切点(piontcut)
 * 根据所使用的注解以及缓存的状态，这个切面会从缓存中获取数据，
 * 将数据添加到缓存之中或者从缓存中移除某个值
 */
@Configuration
@EnableAutoConfiguration
@EnableCaching
public class RedisCacheConfig extends CachingConfigurerSupport {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 6379;
    private static final int TIMEOUT = 30;
    private static final String PASSWORD = "";
    private static final int DATABASE = 3;

    private RedisTemplate redisTemplate;
    /**
     * 连接redis的工厂类
     * @return
     */
    @Bean
    public RedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName(HOST);
        factory.setPort(PORT);
        factory.setTimeout(TIMEOUT);
        factory.setPassword(PASSWORD);
        factory.setDatabase(DATABASE);
        return factory;
    }

    /**
     * 配置RedisTemplate
     * 设置添加序列化器
     * key 使用string序列化器
     * value 使用Json序列化器
     * 还有一种简答的设置方式，改变defaultSerializer对象的实现。
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        //StringRedisTemplate的构造方法中默认设置了stringSerializer
        RedisTemplate<String, Object> template = new RedisTemplate<String,Object>();
        //set key serializer
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);

        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        //set value serializer
        template.setDefaultSerializer(jackson2JsonRedisSerializer);

        template.setConnectionFactory(jedisConnectionFactory());
        template.afterPropertiesSet();

        this.redisTemplate = template;
        return template;
    }

}
