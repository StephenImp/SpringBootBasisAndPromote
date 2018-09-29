package com.cn.cacheable.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by admin on 2018/6/7.
 */
@Controller
public class RedisStringDemoController {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @RequestMapping(value="/test/redis", method= RequestMethod.GET)
    @ResponseBody
    public void TestRedis() {
        ValueOperations<String,Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set("hello", "redis");
        System.out.println("useRedisDao = " + valueOperations.get("hello"));

    }

    /**
     * redisTemplate对String类型的数据进行操作
     * ValueOperations可以对String数据结构进行操作
     */
    @RequestMapping(value="/test/redis/string", method= RequestMethod.GET)
    @ResponseBody
    public void TestRedisString() {

        /**
         * set void set(K key, V value);
         */
        ValueOperations<String,Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set("name", "StephenImp");
        System.out.println("useRedisDao = " + valueOperations.get("name"));


        /**
         * set void set(K key, V value, long timeout, TimeUnit unit);
         */
        redisTemplate.opsForValue().set("name","tom",10, TimeUnit.SECONDS);
        redisTemplate.opsForValue().get("name");//由于设置的是10秒失效，十秒之内查询有结果，十秒之后返回为null


        /**
         * set void set(K key, V value, long offset);
         * 该方法是用 value 参数覆写(overwrite)给定 key 所储存的字符串值，从偏移量 offset 开始
         */
        redisTemplate.opsForValue().set("key","hello world");
        redisTemplate.opsForValue().set("key","redis", 6);
        System.out.println("***************"+redisTemplate.opsForValue().get("key"));
        //结果：***************hello redis


        /**
         * setIfAbsent Boolean setIfAbsent(K key, V value);
         */
        System.out.println(redisTemplate.opsForValue().setIfAbsent("multi1","multi1"));//false  multi1之前已经存在
        System.out.println(redisTemplate.opsForValue().setIfAbsent("multi111","multi111"));//true  multi111之前不存在
        //结果:false      true


        /**
         * multiSet void multiSet(Map<? extends K, ? extends V> m);
         * 为多个键分别设置它们的值
         * 把值放在放在Map里，key放在List里，一起取出来
         */

        Map<String,String> maps = new HashMap<String, String>();
        maps.put("multi1","multi1");
        maps.put("multi2","multi2");
        maps.put("multi3","multi3");
        redisTemplate.opsForValue().multiSet(maps);
        List<String> keys = new ArrayList<String>();
        keys.add("multi1");
        keys.add("multi2");
        keys.add("multi3");
        System.out.println(redisTemplate.opsForValue().multiGet(keys));
        //结果：[multi1, multi2, multi3]


        /**
         * multiSetIfAbsent Boolean multiSetIfAbsent(Map<? extends K, ? extends V> m);
         * 为多个键分别设置它们的值，如果存在则返回false，不存在返回true
         */

        Map<String,String> maps2 = new HashMap<String, String>();
        maps2.put("multi11","multi11");
        maps2.put("multi22","multi22");
        maps2.put("multi33","multi33");
        Map<String,String> maps3 = new HashMap<String, String>();
        maps3.put("multi1","multi1");
        maps3.put("multi2","multi2");
        maps3.put("multi3","multi3");
        System.out.println(redisTemplate.opsForValue().multiSetIfAbsent(maps2));
        System.out.println(redisTemplate.opsForValue().multiSetIfAbsent(maps3));
        //结果：true    false



        /**
         * getAndSet V getAndSet(K key, V value);
         * 设置键的字符串值并返回其旧值
         */
        redisTemplate.opsForValue().set("getSetTest","test");
        System.out.println(redisTemplate.opsForValue().getAndSet("getSetTest","test2"));
        //结果：test


        /**
         * increment Long increment(K key, long delta);
         * 支持整数
         */
        redisTemplate.opsForValue().increment("increlong",1);
        System.out.println("***************"+redisTemplate.opsForValue().get("increlong"));
        //结果：***************1


        /**
         * increment Double increment(K key, double delta);
         *  也支持浮点数
         */
        redisTemplate.opsForValue().increment("increlong",1.2);
        System.out.println("***************"+redisTemplate.opsForValue().get("increlong"));
        //结果：***************2.2


        /**
         * append Integer append(K key, String value);
           如果key已经存在并且是一个字符串，则该命令将该值追加到字符串的末尾。
           如果键不存在，则它被创建并设置为空字符串，因此APPEND在这种特殊情况下将类似于SET。
         */
        redisTemplate.opsForValue().append("appendTest","Hello");
        System.out.println(redisTemplate.opsForValue().get("appendTest"));
        redisTemplate.opsForValue().append("appendTest","world");
        System.out.println(redisTemplate.opsForValue().get("appendTest"));
        //结果：Hello   Helloworld

        /**
         * get String get(K key, long start, long end);
         * 截取key所对应的value字符串
         */
        //使用：appendTest对应的value为Helloworld
        System.out.println("*********"+redisTemplate.opsForValue().get("appendTest",0,5));
        //结果：*********Hellow
        //使用：
        System.out.println("*********"+redisTemplate.opsForValue().get("appendTest",0,-1));
        //结果：*********Helloworld
        //使用：
        System.out.println("*********"+redisTemplate.opsForValue().get("appendTest",-3,-1));
        //结果：*********rld

        /**
         * size Long size(K key);
           返回key所对应的value值得长度
         */
        //使用：
        redisTemplate.opsForValue().set("key","hello world");
        System.out.println("***************"+redisTemplate.opsForValue().size("key"));
        //结果：***************11


        /**
         * setBit Boolean setBit(K key, long offset, boolean value);
           对 key 所储存的字符串值，设置或清除指定偏移量上的位(bit)
           key键对应的值value对应的ascii码,在offset的位置(从左向右数)变为value
         */
       // 使用：
        redisTemplate.opsForValue().set("bitTest","a");
        // 'a' 的ASCII码是 97。转换为二进制是：01100001
        // 'b' 的ASCII码是 98  转换为二进制是：01100010
        // 'c' 的ASCII码是 99  转换为二进制是：01100011
        //因为二进制只有0和1，在setbit中true为1，false为0，因此我要变为'b'的话第六位设置为1，第七位设置为0
        redisTemplate.opsForValue().setBit("bitTest",6, true);
        redisTemplate.opsForValue().setBit("bitTest",7, false);
        System.out.println(redisTemplate.opsForValue().get("bitTest"));
        //结果：b


        /**
         * getBit Boolean getBit(K key, long offset);
           获取键对应值的ascii码的在offset处位值
         */
        //使用：
        System.out.println(redisTemplate.opsForValue().getBit("bitTest",7));
        //结果：false

    }

}
