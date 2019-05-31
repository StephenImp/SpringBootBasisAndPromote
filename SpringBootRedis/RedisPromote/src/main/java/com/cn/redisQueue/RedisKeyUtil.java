package com.cn.redisQueue;

/**
 * Created by MOZi on 2019/3/22.
 * 存入Redis中时要存到key对应的集合中，所以要写个产生key的工具类：
 */
public class RedisKeyUtil {

    private static String EVENT_KEY = "ASYNC_EVENT_KEY";

    public static String getEventQueueKey(){
        return EVENT_KEY;
    }
}
