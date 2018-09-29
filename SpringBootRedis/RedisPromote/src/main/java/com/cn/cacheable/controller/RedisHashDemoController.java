package com.cn.cacheable.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2018/6/11.
 */
@Controller
public class RedisHashDemoController {
    @Autowired
    private RedisTemplate<String,Object> template;

    /**
     * Redis的散列可以让用户将多个键值对存储到一个Redis键里面。
       public interface HashOperations<H,HK,HV>
       HashOperations提供一系列方法操作hash：
     */

    @RequestMapping(value="/test/redis/hash", method= RequestMethod.GET)
    @ResponseBody
    public void TestRedisList() {

        //初始数据:
        template.opsForHash().put("redisHash","name","tom");
        template.opsForHash().put("redisHash","age",26);
        template.opsForHash().put("redisHash","class","6");

        Map<String,Object> testMap = new HashMap();
        testMap.put("name","jack");
        testMap.put("age",27);
        testMap.put("class","1");
        template.opsForHash().putAll("redisHash1",testMap);

        /**
         * Long delete(H key, Object... hashKeys);
           删除给定的哈希hashKeys
         */
        //使用：
        System.out.println(template.opsForHash().delete("redisHash","name"));
        System.out.println(template.opsForHash().entries("redisHash"));
        //结果：1
        //{class=6, age=28.1}

        /**
         * Boolean hasKey(H key, Object hashKey);
           确定哈希hashKey是否存在
         */
        //使用：
        System.out.println(template.opsForHash().hasKey("redisHash","age"));
        System.out.println(template.opsForHash().hasKey("redisHash","ttt"));
        //结果：true
        //false

        /**
         * HV get(H key, Object hashKey);
           从键中的哈希获取给定hashKey的值
         */
        //使用：
        System.out.println(template.opsForHash().get("redisHash","age"));
        //结果：26

        /**
         * List<HV> multiGet(H key, Collection<HK> hashKeys);
           从哈希中获取给定hashKey的值
         */
        //使用：
        List<Object> kes = new ArrayList<Object>();
        kes.add("name");
        kes.add("age");
        System.out.println(template.opsForHash().multiGet("redisHash",kes));
        //结果：[jack, 28.1]

        /**
         * Long increment(H key, HK hashKey, long delta);
           通过给定的delta增加散列hashKey的值（整型）
         */
        //使用：
        System.out.println(template.opsForHash().get("redisHash","age"));
        System.out.println(template.opsForHash().increment("redisHash","age",1));
        //结果：
        //26
        //27

        /**
         * Double increment(H key, HK hashKey, double delta);
           通过给定的delta增加散列hashKey的值（浮点数）
         */
        //使用：
        System.out.println(template.opsForHash().get("redisHash","age"));
        System.out.println(template.opsForHash().increment("redisHash","age",1.1));
        //结果：
        //27
        //28.1

        /**
         * Set<HK> keys(H key);
           获取key所对应的散列表的key
         */
        //使用：
        System.out.println(template.opsForHash().keys("redisHash1"));
        //redisHash1所对应的散列表为{class=1, name=jack, age=27}
        //结果：[name, class, age]

        /**
         * Long size(H key);
           获取key所对应的散列表的大小个数
         */
        //使用：
        System.out.println(template.opsForHash().size("redisHash1"));
        //redisHash1所对应的散列表为{class=1, name=jack, age=27}
        //结果：3

        /**
         * void putAll(H key, Map<? extends HK, ? extends HV> m);
           使用m中提供的多个散列字段设置到key对应的散列表中
         */
        //使用：
        Map<String,Object> testMap1 = new HashMap();
        testMap.put("name","jack");
        testMap.put("age",27);
        testMap.put("class","1");
        template.opsForHash().putAll("redisHash1",testMap1);
        System.out.println(template.opsForHash().entries("redisHash1"));
        //结果：{class=1, name=jack, age=27}

        /**
         * void put(H key, HK hashKey, HV value);
           设置散列hashKey的值
         */
        //使用：
        template.opsForHash().put("redisHash","name","tom");
        template.opsForHash().put("redisHash","age",26);
        template.opsForHash().put("redisHash","class","6");
        System.out.println(template.opsForHash().entries("redisHash"));
        //结果：{age=26, class=6, name=tom}

        /**
         * Boolean putIfAbsent(H key, HK hashKey, HV value);
           仅当hashKey不存在时才设置散列hashKey的值。
         */
        //使用：
        System.out.println(template.opsForHash().putIfAbsent("redisHash","age",30));
        System.out.println(template.opsForHash().putIfAbsent("redisHash","kkk","kkk"));
        //结果：
        //false
        //true

        /**
         * List<HV> values(H key);
           获取整个哈希存储的值根据密钥
         */
        //使用：
        System.out.println(template.opsForHash().values("redisHash"));
        //结果：[tom, 26, 6]

        /**
         * Map<HK, HV> entries(H key);
           获取整个哈希存储根据密钥
         */
        //使用：
        System.out.println(template.opsForHash().entries("redisHash"));
        //结果：{age=26, class=6, name=tom}

        /**
         * Cursor<Map.Entry<HK, HV>> scan(H key, ScanOptions options);
           使用Cursor在key的hash中迭代，相当于迭代器。
         */
        //使用：
        Cursor<Map.Entry<Object, Object>> curosr = template.opsForHash().scan("redisHash",ScanOptions.NONE);
        while(curosr.hasNext()){
            Map.Entry<Object, Object> entry = curosr.next();
            System.out.println(entry.getKey()+":"+entry.getValue());
        }
        //结果：
        //age:28.1
        //class:6
        //kkk:kkk
    }
}
