package com.cn.ranking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by admin on 2018/6/12.
 */
@Controller
public class RankRedisDemoController {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @RequestMapping(value = "test/redis/rank",method = RequestMethod.POST)
    @ResponseBody
    public void testRedisVisit(){

        //创建4篇文章,用hash存储
        redisTemplate.opsForHash().put("article:00001","title","文章1");
        redisTemplate.opsForHash().put("article:00002","title","文章2");
        redisTemplate.opsForHash().put("article:00003","title","文章3");
        redisTemplate.opsForHash().put("article:00004","title","文章4");


        Set<Object> stringStringMap = redisTemplate.opsForHash().keys("article:00004");
        System.out.println(stringStringMap);


        //创建一个有序集合，用来存文章的点赞排名。
        redisTemplate.opsForZSet().add("score","article:00001",0 );
        redisTemplate.opsForZSet().add("score","article:00002",0 );
        redisTemplate.opsForZSet().add("score","article:00003",0 );
        redisTemplate.opsForZSet().add("score","article:00004",0 );

        System.out.println("文章获取到的"+redisTemplate.opsForZSet().range("score", 0, -1)
                .stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList()));

        //现在模拟用户点赞。
        //用户点赞就在该文章的基础上+1
        redisTemplate.opsForZSet().incrementScore("score","article:00001",1);
        redisTemplate.opsForZSet().incrementScore("score","article:00002",1);
        redisTemplate.opsForZSet().incrementScore("score","article:00003",1);
        redisTemplate.opsForZSet().incrementScore("score","article:00004",1);
        redisTemplate.opsForZSet().incrementScore("score","article:00002",1);
        redisTemplate.opsForZSet().incrementScore("score","article:00003",1);
        redisTemplate.opsForZSet().incrementScore("score","article:00002",1);
        redisTemplate.opsForZSet().incrementScore("score","article:00001",1);
        redisTemplate.opsForZSet().incrementScore("score","article:00004",1);
        redisTemplate.opsForZSet().incrementScore("score","article:00003",1);
        redisTemplate.opsForZSet().incrementScore("score","article:00003",1);
        redisTemplate.opsForZSet().incrementScore("score","article:00003",1);

        //打印输出的list
        Set<ZSetOperations.TypedTuple<String>> score = redisTemplate.opsForZSet().rangeWithScores("score", 0, -1);
        Iterator<ZSetOperations.TypedTuple<String>> iterator = score.iterator();
        while (iterator.hasNext()){
            ZSetOperations.TypedTuple<String> typedTuple = iterator.next();
            System.out.println("value:" + typedTuple.getValue() + "score:" + typedTuple.getScore());
        }
    }

}
