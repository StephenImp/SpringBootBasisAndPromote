package com.cn.notification;

import com.cn.RedisClient;
import redis.clients.jedis.Jedis;

public class TestJedis {

	public static void main(String[] args) {
        Jedis jedis = RedisClient.jedisPool.getResource();
        jedis.set("notify", "Test redis Listen");
        //jedis.expire("notify", 10);
        jedis.del("notify");
	}

}
