package com.cn;

import com.alibaba.fastjson.JSON;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;
import java.util.ResourceBundle;

public class RedisClient {

    public static JedisPool jedisPool; // 池化管理jedis链接池

    static {
        // 读取相关的配置:redis.properties
        ResourceBundle resourceBundle = ResourceBundle.getBundle("application");

        //int maxActive = Integer.parseInt(resourceBundle.getString("redis.pool.maxActive"));
        int maxActive = 100;
        //int maxIdle = Integer.parseInt(resourceBundle.getString("redis.pool.maxIdle"));
        int maxIdle = 5000;
        //int maxWait = Integer.parseInt(resourceBundle.getString("redis.pool.maxWait"));
        int maxWait = 10000;
        String ip = "47.99.114.23";
        int port = 6379;
        String password = "7c4a8d09ik0g4";


        JedisPoolConfig config = new JedisPoolConfig();
        // 设置最大连接数
        config.setMaxTotal(maxActive);
        // 设置最大空闲数
        config.setMaxIdle(maxIdle);
        // 设置超时时间
        config.setMaxWaitMillis(maxWait);

        // 初始化连接池
        jedisPool = new JedisPool(config, ip, port,999999,password);
    }

    /**
     * 向缓存中设置字符串内容
     *
     * @param key   key
     * @param value value
     * @return
     * @throws Exception
     */
    public static boolean set(String key, String value) throws Exception {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * 向缓存中设置对象
     *
     * @param key
     * @param value
     * @return
     */
    public static boolean set(String key, Object value) {
        Jedis jedis = null;
        try {
            String objectJson = JSON.toJSONString(value);
            jedis = jedisPool.getResource();  // 从池中获取一个Jedis对象
            jedis.set(key, objectJson);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            jedisPool.returnResource(jedis);//释放对象池
        }
    }

    /**
     * 删除缓存中得对象，根据key
     *
     * @param key
     * @return
     */
    public static boolean del(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(key);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * 根据key 获取内容
     *
     * @param key
     * @return
     */
    public static Object get(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Object value = jedis.get(key);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * 根据key 获取对象
     *
     * @param key
     * @return
     */
    public static <T> T get(String key, Class<T> clazz) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String value = jedis.get(key);
            return JSON.parseObject(value, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * 根据key 获取对象
     *
     * @param key
     * @return
     */
    public static <T> List<T> getList(String key, Class<T> clazz) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String value = jedis.get(key);
            return JSON.parseArray(value, clazz);
        } catch (Exception e) {
//			e.printStackTrace();
            return null;
        } finally {
            try {
                jedisPool.returnResource(jedis);
            } catch (Exception e) {
                System.out.println("");
            }
        }
    }

    public static void flushAll() {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.flushAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

}
