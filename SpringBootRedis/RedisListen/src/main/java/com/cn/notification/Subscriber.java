package com.cn.notification;


import com.cn.RedisClient;
import redis.clients.jedis.Jedis;

/**
 * 订阅者
 */
public class Subscriber {

	public static void main(String[] args) {
		Jedis jedis = RedisClient.jedisPool.getResource();
        jedis.psubscribe(new KeyExpiredListener(), "__key*__:*");

	}

}
