package com.cn.cacheable.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Created by admin on 2018/6/11.
 */
@Controller
public class RedisZsetDemoController {

    @Autowired
    private RedisTemplate<String,Object> template;

    /**
     * Redis 有序集合和无序集合一样也是string类型元素的集合,且不允许重复的成员。
       不同的是每个元素都会关联一个double类型的分数。redis正是通过分数来为集合中的成员进行从小到大的排序。
       有序集合的成员是唯一的,但分数(score)却可以重复。
     */

    @RequestMapping(value="/test/redis/zset", method= RequestMethod.GET)
    @ResponseBody
    public void TestRedisZset() {

        ValueOperations<String,Object> valueOperations = template.opsForValue();

        /**
         * Boolean add(K key, V value, double score);
           新增一个有序集合，存在的话为false，不存在的话为true
         */
        //使用：
        System.out.println(template.opsForZSet().add("zset1","zset-1",1.0));
        //结果：true

        /**
         * Long add(K key, Set<TypedTuple<V>> tuples);
           新增一个有序集合
         */
        //使用：
        ZSetOperations.TypedTuple<Object> objectTypedTuple1 = new DefaultTypedTuple<Object>("zset-5",9.6);
        ZSetOperations.TypedTuple<Object> objectTypedTuple2 = new DefaultTypedTuple<Object>("zset-6",9.9);
        Set<ZSetOperations.TypedTuple<Object>> tuples = new HashSet<ZSetOperations.TypedTuple<Object>>();
        tuples.add(objectTypedTuple1);
        tuples.add(objectTypedTuple2);
        System.out.println(template.opsForZSet().add("zset1",tuples));
        System.out.println(template.opsForZSet().range("zset1",0,-1));
        //结果：[zset-1, zset-2, zset-3, zset-4, zset-5, zset-6]

        /**
         * Long remove(K key, Object... values);
           从有序集合中移除一个或者多个元素
         */

        //使用：
        System.out.println(template.opsForZSet().range("zset1",0,-1));
        System.out.println(template.opsForZSet().remove("zset1","zset-6"));
        System.out.println(template.opsForZSet().range("zset1",0,-1));
        //结果：[zset-1, zset-2, zset-3, zset-4, zset-5, zset-6]
        //1
        //[zset-1, zset-2, zset-3, zset-4, zset-5]

        /**
         * Double incrementScore(K key, V value, double delta);
           增加元素的score值，并返回增加后的值
         */
        //使用：
        System.out.println(template.opsForZSet().incrementScore("zset1","zset-1",1.1));  //原为1.1
        //结果：2.2

        /**
         * Long rank(K key, Object o);
           返回有序集中指定成员的排名，其中有序集成员按分数值递增(从小到大)顺序排列
         */
        //使用：
        System.out.println(template.opsForZSet().range("zset1",0,-1));
        System.out.println(template.opsForZSet().rank("zset1","zset-2"));
        //结果：[zset-2, zset-1, zset-3, zset-4, zset-5]
        //0   //表明排名第一

        /**
         * Long reverseRank(K key, Object o);
           返回有序集中指定成员的排名，其中有序集成员按分数值递减(从大到小)顺序排列
         */
        //使用：
        System.out.println(template.opsForZSet().range("zset1",0,-1));
        System.out.println(template.opsForZSet().reverseRank("zset1","zset-2"));
        //结果：[zset-2, zset-1, zset-3, zset-4, zset-5]
        //4 //递减之后排到第五位去了


        /**
         * Set<V> range(K key, long start, long end);
           通过索引区间返回有序集合成指定区间内的成员，其中有序集成员按分数值递增(从小到大)顺序排列
         */
        //使用：
        System.out.println(template.opsForZSet().range("zset1",0,-1));
        //结果：[zset-2, zset-1, zset-3, zset-4, zset-5]


        /**
         * Set<TypedTuple<V>> rangeWithScores(K key, long start, long end);
           通过索引区间返回有序集合成指定区间内的成员对象，其中有序集成员按分数值递增(从小到大)顺序排列
         */
       // 使用：
        Set<ZSetOperations.TypedTuple<Object>> tuples2 = template.opsForZSet().rangeWithScores("zset1",0,-1);
        Iterator<ZSetOperations.TypedTuple<Object>> iterator = tuples2.iterator();
        while (iterator.hasNext())
        {
            ZSetOperations.TypedTuple<Object> typedTuple = iterator.next();
            System.out.println("value:" + typedTuple.getValue() + "score:" + typedTuple.getScore());
        }
        //结果：value:zset-2score:1.2
        //value:zset-1score:2.2
        //value:zset-3score:2.3
        //value:zset-4score:6.6
        //value:zset-5score:9.6


        /**
         * Set<V> rangeByScore(K key, double min, double max);
           通过分数返回有序集合指定区间内的成员，其中有序集成员按分数值递增(从小到大)顺序排列
         */
        //使用：
        System.out.println(template.opsForZSet().rangeByScore("zset1",0,5));
        //结果：[zset-2, zset-1, zset-3]

        /**
         * Set<TypedTuple<V>> rangeByScoreWithScores(K key, double min, double max);
           通过分数返回有序集合指定区间内的成员对象，其中有序集成员按分数值递增(从小到大)顺序排列
         */
        //使用：
        Set<ZSetOperations.TypedTuple<Object>> tuples3 = template.opsForZSet().rangeByScoreWithScores("zset1",0,5);
        Iterator<ZSetOperations.TypedTuple<Object>> iterator3 = tuples3.iterator();
        while (iterator.hasNext())
        {
            ZSetOperations.TypedTuple<Object> typedTuple = iterator3.next();
            System.out.println("value:" + typedTuple.getValue() + "score:" + typedTuple.getScore());
        }
        //结果：
        //value:zset-2score:1.2
        //value:zset-1score:2.2
        //value:zset-3score:2.3


        /**
         * Set<V> rangeByScore(K key, double min, double max, long offset, long count);
           通过分数返回有序集合指定区间内的成员，并在索引范围内，其中有序集成员按分数值递增(从小到大)顺序排列
         */

        //使用：
        System.out.println(template.opsForZSet().rangeByScore("zset1",0,5));
        System.out.println(template.opsForZSet().rangeByScore("zset1",0,5,1,2));
        //结果：[zset-2, zset-1, zset-3]
        //[zset-1, zset-3]

        /**
         * Set<TypedTuple<V>> rangeByScoreWithScores(K key, double min, double max, long offset, long count);
         * 通过分数返回有序集合指定区间内的成员对象，并在索引范围内，其中有序集成员按分数值递增(从小到大)顺序排列
         */

        //使用：
        Set<ZSetOperations.TypedTuple<Object>> tuples5 = template.opsForZSet().rangeByScoreWithScores("zset1",0,5,1,2);
        Iterator<ZSetOperations.TypedTuple<Object>> iterator5 = tuples5.iterator();
        while (iterator.hasNext())
        {
            ZSetOperations.TypedTuple<Object> typedTuple = iterator.next();
            System.out.println("value:" + typedTuple.getValue() + "score:" + typedTuple.getScore());
        }
        //结果：
        // value:zset-1score:2.2
        //value:zset-3score:2.3


        /**
         * Set<V> reverseRange(K key, long start, long end);
           通过索引区间返回有序集合成指定区间内的成员，其中有序集成员按分数值递减(从大到小)顺序排列
         */
        //使用：
        System.out.println(template.opsForZSet().reverseRange("zset1",0,-1));
        //结果：[zset-5, zset-4, zset-3, zset-1, zset-2]


        /**
         * Set<TypedTuple<V>> reverseRangeWithScores(K key, long start, long end);
           通过索引区间返回有序集合成指定区间内的成员对象，其中有序集成员按分数值递减(从大到小)顺序排列
         */
        //使用：
        Set<ZSetOperations.TypedTuple<Object>> tuples6 = template.opsForZSet().reverseRangeWithScores("zset1",0,-1);
        Iterator<ZSetOperations.TypedTuple<Object>> iterator6 = tuples6.iterator();
        while (iterator.hasNext())
        {
            ZSetOperations.TypedTuple<Object> typedTuple = iterator.next();
            System.out.println("value:" + typedTuple.getValue() + "score:" + typedTuple.getScore());
        }
        //结果：
        //value:zset-5score:9.6
        //value:zset-4score:6.6
        //value:zset-3score:2.3
        //value:zset-1score:2.2
        //value:zset-2score:1.2

        /**
         * Set<V> reverseRangeByScore(K key, double min, double max);
         * 使用：与rangeByScore调用方法一样，其中有序集成员按分数值递减(从大到小)顺序排列
         */

        /**
         * Set<TypedTuple<V>> reverseRangeByScoreWithScores(K key, double min, double max);
         * 使用：与rangeByScoreWithScores调用方法一样，其中有序集成员按分数值递减(从大到小)顺序排列
         */

        /**
         * Set<V> reverseRangeByScore(K key, double min, double max, long offset, long count);
         * 使用：与rangeByScore调用方法一样，其中有序集成员按分数值递减(从大到小)顺序排列
         */

        /**
         * Set<TypedTuple<V>> reverseRangeByScoreWithScores(K key, double min, double max, long offset, long count);
         * 使用：与rangeByScoreWithScores调用方法一样，其中有序集成员按分数值递减(从大到小)顺序排列
         */

        /**
         * Long count(K key, double min, double max);
           通过分数返回有序集合指定区间内的成员个数
         */
        //使用：
        System.out.println(template.opsForZSet().rangeByScore("zset1",0,5));
        System.out.println(template.opsForZSet().count("zset1",0,5));
        //结果：[zset-2, zset-1, zset-3]
        //3

        /**
         * Long size(K key);
           获取有序集合的成员数，内部调用的就是zCard方法
         */

        //使用：
        System.out.println(template.opsForZSet().size("zset1"));
        //结果：6

        /**
         * Long zCard(K key);
           获取有序集合的成员数
         */
        //使用：
        System.out.println(template.opsForZSet().zCard("zset1"));
        //结果：6

        /**
         * Double score(K key, Object o);
           获取指定成员的score值
         */
        //使用：
        System.out.println(template.opsForZSet().score("zset1","zset-1"));
        //结果：2.2

        /**
         * Long removeRange(K key, long start, long end);
           移除指定索引位置的成员，其中有序集成员按分数值递增(从小到大)顺序排列
         */
        //使用：
        System.out.println(template.opsForZSet().range("zset2",0,-1));
        System.out.println(template.opsForZSet().removeRange("zset2",1,2));
        System.out.println(template.opsForZSet().range("zset2",0,-1));
        //结果：[zset-1, zset-2, zset-3, zset-4]
        //2
        //[zset-1, zset-4]

        /**
         * Long removeRangeByScore(K key, double min, double max);
           根据指定的score值得范围来移除成员
         */
        //使用：
        //System.out.println(template.opsForZSet().add("zset2","zset-1",1.1));
        //System.out.println(template.opsForZSet().add("zset2","zset-2",1.2));
        //System.out.println(template.opsForZSet().add("zset2","zset-3",2.3));
        //System.out.println(template.opsForZSet().add("zset2","zset-4",6.6));
        System.out.println(template.opsForZSet().range("zset2",0,-1));
        System.out.println(template.opsForZSet().removeRangeByScore("zset2",2,3));
        System.out.println(template.opsForZSet().range("zset2",0,-1));
        //结果：[zset-1, zset-2, zset-3,zset-4]
        //1
        //[zset-1, zset-2, zset-4]

        /**
         * Long unionAndStore(K key, K otherKey, K destKey);
           计算给定的一个有序集的并集，并存储在新的 destKey中，key相同的话会把score值相加
         */
        //使用：
        System.out.println(template.opsForZSet().add("zzset1","zset-1",1.0));
        System.out.println(template.opsForZSet().add("zzset1","zset-2",2.0));
        System.out.println(template.opsForZSet().add("zzset1","zset-3",3.0));
        System.out.println(template.opsForZSet().add("zzset1","zset-4",6.0));

        System.out.println(template.opsForZSet().add("zzset2","zset-1",1.0));
        System.out.println(template.opsForZSet().add("zzset2","zset-2",2.0));
        System.out.println(template.opsForZSet().add("zzset2","zset-3",3.0));
        System.out.println(template.opsForZSet().add("zzset2","zset-4",6.0));
        System.out.println(template.opsForZSet().add("zzset2","zset-5",7.0));
        System.out.println(template.opsForZSet().unionAndStore("zzset1","zzset2","destZset11"));

        Set<ZSetOperations.TypedTuple<Object>> tuples8 = template.opsForZSet().rangeWithScores("destZset11",0,-1);
        Iterator<ZSetOperations.TypedTuple<Object>> iterator8 = tuples8.iterator();
        while (iterator.hasNext())
        {
            ZSetOperations.TypedTuple<Object> typedTuple = iterator.next();
            System.out.println("value:" + typedTuple.getValue() + "score:" + typedTuple.getScore());
        }
        //结果：
        //value:zset-1score:2.0
        //value:zset-2score:4.0
        //value:zset-3score:6.0
        //value:zset-5score:7.0
        //value:zset-4score:12.0

        /**
         * Long unionAndStore(K key, Collection<K> otherKeys, K destKey);
           计算给定的多个有序集的并集，并存储在新的 destKey中
         */
        //使用：
        //System.out.println(template.opsForZSet().add("zzset1","zset-1",1.0));
        //System.out.println(template.opsForZSet().add("zzset1","zset-2",2.0));
        //System.out.println(template.opsForZSet().add("zzset1","zset-3",3.0));
        //System.out.println(template.opsForZSet().add("zzset1","zset-4",6.0));
        //
        //System.out.println(template.opsForZSet().add("zzset2","zset-1",1.0));
        //System.out.println(template.opsForZSet().add("zzset2","zset-2",2.0));
        //System.out.println(template.opsForZSet().add("zzset2","zset-3",3.0));
        //System.out.println(template.opsForZSet().add("zzset2","zset-4",6.0));
        //System.out.println(template.opsForZSet().add("zzset2","zset-5",7.0));

        System.out.println(template.opsForZSet().add("zzset3","zset-1",1.0));
        System.out.println(template.opsForZSet().add("zzset3","zset-2",2.0));
        System.out.println(template.opsForZSet().add("zzset3","zset-3",3.0));
        System.out.println(template.opsForZSet().add("zzset3","zset-4",6.0));
        System.out.println(template.opsForZSet().add("zzset3","zset-5",7.0));

        List<String> stringList = new ArrayList<String>();
        stringList.add("zzset2");
        stringList.add("zzset3");
        System.out.println(template.opsForZSet().unionAndStore("zzset1",stringList,"destZset22"));

        Set<ZSetOperations.TypedTuple<Object>> tuples9 = template.opsForZSet().rangeWithScores("destZset22",0,-1);
        Iterator<ZSetOperations.TypedTuple<Object>> iterator9 = tuples9.iterator();
        while (iterator.hasNext())
        {
            ZSetOperations.TypedTuple<Object> typedTuple = iterator.next();
            System.out.println("value:" + typedTuple.getValue() + "score:" + typedTuple.getScore());
        }
        //结果：
        //value:zset-1score:3.0
        //value:zset-2score:6.0
        //value:zset-3score:9.0
        //value:zset-5score:14.0
        //value:zset-4score:18.0

        /**
         * Long intersectAndStore(K key, K otherKey, K destKey);
           计算给定的一个或多个有序集的交集并将结果集存储在新的有序集合 key 中
         */
        //使用：
        System.out.println(template.opsForZSet().intersectAndStore("zzset1","zzset2","destZset33"));

        Set<ZSetOperations.TypedTuple<Object>> tuples10 = template.opsForZSet().rangeWithScores("destZset33",0,-1);
        Iterator<ZSetOperations.TypedTuple<Object>> iterator10 = tuples10.iterator();
        while (iterator.hasNext())
        {
            ZSetOperations.TypedTuple<Object> typedTuple = iterator.next();
            System.out.println("value:" + typedTuple.getValue() + "score:" + typedTuple.getScore());
        }
        //结果：
        //value:zset-1score:2.0
        //value:zset-2score:4.0
        //value:zset-3score:6.0
        //value:zset-4score:12.0

        /**
         * Long intersectAndStore(K key, Collection<K> otherKeys, K destKey);
           计算给定的一个或多个有序集的交集并将结果集存储在新的有序集合 key 中
         */
        //使用：
        List<String> stringList11 = new ArrayList<String>();
        stringList.add("zzset2");
        stringList.add("zzset3");
        System.out.println(template.opsForZSet().intersectAndStore("zzset1",stringList11,"destZset44"));

        Set<ZSetOperations.TypedTuple<Object>> tuples11 = template.opsForZSet().rangeWithScores("destZset44",0,-1);
        Iterator<ZSetOperations.TypedTuple<Object>> iterator11 = tuples11.iterator();
        while (iterator.hasNext())
        {
            ZSetOperations.TypedTuple<Object> typedTuple = iterator.next();
            System.out.println("value:" + typedTuple.getValue() + "score:" + typedTuple.getScore());
        }
        //结果：
        //value:zset-1score:3.0
        //value:zset-2score:6.0
        //value:zset-3score:9.0
        //value:zset-4score:18.0


        /**
         * Cursor<TypedTuple<V>> scan(K key, ScanOptions options);
           遍历zset
         */

        //使用：
        Cursor<ZSetOperations.TypedTuple<Object>> cursor = template.opsForZSet().scan("zzset1", ScanOptions.NONE);
        while (cursor.hasNext()){
            ZSetOperations.TypedTuple<Object> item = cursor.next();
            System.out.println(item.getValue() + ":" + item.getScore());
        }
        //结果：
        //zset-1:1.0
        //zset-2:2.0
        //zset-3:3.0
        //zset-4:6.0

    }
}
