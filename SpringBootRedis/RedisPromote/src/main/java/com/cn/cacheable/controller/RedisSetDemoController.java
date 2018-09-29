package com.cn.cacheable.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/6/11.
 */
public class RedisSetDemoController {

    @Autowired
    private RedisTemplate<String,Object> template;

    /**
     Redis的Set是string类型的无序集合。集合成员是唯一的，这就意味着集合中不能出现重复的数据。
     Redis 中 集合是通过哈希表实现的，所以添加，删除，查找的复杂度都是O(1)。
     public interface SetOperations<K,V>
     SetOperations提供了对无序集合的一系列操作：
     */

    @RequestMapping(value="/test/redis/set", method= RequestMethod.GET)
    @ResponseBody
    public void TestRedisSet() {

        ValueOperations<String,Object> valueOperations = template.opsForValue();
        /**
         * Long add(K key, V... values);
           无序集合中添加元素，返回添加个数
           也可以直接在add里面添加多个值
           如：template.opsForSet().add("setTest","aaa","bbb")
         */

        //使用：
        String[] strArrays = new String[]{"strarr1","sgtarr2"};
        System.out.println(template.opsForSet().add("setTest", strArrays));
        //结果：2


        /**
         * Long remove(K key, Object... values);
           移除集合中一个或多个成员
         */
        //使用：
        String[] strArrays1 = new String[]{"strarr1","sgtarr2"};
        System.out.println(template.opsForSet().remove("setTest",strArrays1));
        //结果：2


        /**
         * V pop(K key);
           移除并返回集合中的一个随机元素
         */
        //使用：
        System.out.println(template.opsForSet().pop("setTest"));
        System.out.println(template.opsForSet().members("setTest"));
        //结果：bbb
        //[aaa, ccc]


        /**
         * Boolean move(K key, V value, K destKey);
           将 member 元素从 source 集合移动到 destination 集合
         */
        //使用：
        template.opsForSet().move("setTest","aaa","setTest2");
        System.out.println(template.opsForSet().members("setTest"));
        System.out.println(template.opsForSet().members("setTest2"));
        //结果：[ccc]
        //[aaa]

        /**
         * Long size(K key);
           无序集合的大小长度
         */
        //使用：
        System.out.println(template.opsForSet().size("setTest"));
        //结果：1

        /**
         * Boolean isMember(K key, Object o);
           判断 member 元素是否是集合 key 的成员
         */
        //使用：
        System.out.println(template.opsForSet().isMember("setTest","ccc"));
        System.out.println(template.opsForSet().isMember("setTest","asd"));
        //结果：
        //true
        //false


        /**
         * Set<V> intersect(K key, K otherKey);
           key对应的无序集合与otherKey对应的无序集合求交集
         */
        //使用：
        System.out.println(template.opsForSet().members("setTest"));
        System.out.println(template.opsForSet().members("setTest2"));
        System.out.println(template.opsForSet().intersect("setTest","setTest2"));
        //结果：[aaa, ccc]
        //[aaa]
        //[aaa]


        /**
         * Set<V> intersect(K key, Collection<K> otherKeys);
           key对应的无序集合与多个otherKey对应的无序集合求交集
         */
        //使用：
        System.out.println(template.opsForSet().members("setTest"));
        System.out.println(template.opsForSet().members("setTest2"));
        System.out.println(template.opsForSet().members("setTest3"));
        List<String> strlist = new ArrayList<String>();
        strlist.add("setTest2");
        strlist.add("setTest3");
        System.out.println(template.opsForSet().intersect("setTest",strlist));
        //结果：[aaa, ccc]
        //[aaa]
        //[ccc, aaa]
        //[aaa]


        /**
         * Long intersectAndStore(K key, K otherKey, K destKey);
           key无序集合与otherkey无序集合的交集存储到destKey无序集合中
         */
        //使用：
        System.out.println("setTest:" + template.opsForSet().members("setTest"));
        System.out.println("setTest2:" + template.opsForSet().members("setTest2"));
        System.out.println(template.opsForSet().intersectAndStore("setTest","setTest2","destKey1"));
        System.out.println(template.opsForSet().members("destKey1"));
        //结果：
        //setTest:[ddd, bbb, aaa, ccc]
        //setTest2:[ccc, aaa]
        //2
        //[aaa, ccc]


        /**
         * Long intersectAndStore(K key, Collection<K> otherKeys, K destKey);
           key对应的无序集合与多个otherKey对应的无序集合求交集存储到destKey无序集合中
         */
        //使用：
        System.out.println("setTest:" + template.opsForSet().members("setTest"));
        System.out.println("setTest2:" + template.opsForSet().members("setTest2"));
        System.out.println("setTest3:" + template.opsForSet().members("setTest3"));
        List<String> strlist2 = new ArrayList<String>();
        strlist.add("setTest2");
        strlist.add("setTest3");
        System.out.println(template.opsForSet().intersectAndStore("setTest",strlist2,"destKey2"));
        System.out.println(template.opsForSet().members("destKey2"));
        //结果：
        //setTest:[ddd, bbb, aaa, ccc]
        //setTest2:[ccc, aaa]
        //setTest3:[ccc, aaa]
        //2
        //[aaa, ccc]


        /**
         * Set<V> union(K key, K otherKey);
           key无序集合与otherKey无序集合的并集
         */
        //使用：
        System.out.println("setTest:" + template.opsForSet().members("setTest"));
        System.out.println("setTest2:" + template.opsForSet().members("setTest2"));
        System.out.println("setTest3:" + template.opsForSet().members("setTest3"));
        List<String> strlist3 = new ArrayList<String>();
        strlist.add("setTest2");
        strlist.add("setTest3");
        System.out.println(template.opsForSet().union("setTest",strlist3));
        //结果：setTest:[ddd, bbb, aaa, ccc]
        //setTest2:[ccc, aaa]
        //setTest3:[xxx, ccc, aaa]
        //[ddd, xxx, bbb, aaa, ccc]

        /**
         * Long unionAndStore(K key, K otherKey, K destKey);
           key无序集合与otherkey无序集合的并集存储到destKey无序集合中
         */
        //使用：
        System.out.println("setTest:" + template.opsForSet().members("setTest"));
        System.out.println("setTest2:" + template.opsForSet().members("setTest2"));
        System.out.println(template.opsForSet().unionAndStore("setTest","setTest2","unionAndStoreTest1"));
        System.out.println("unionAndStoreTest1:" + template.opsForSet().members("unionAndStoreTest1"));
        //结果：
        //setTest:[ddd, bbb, aaa, ccc]
        //setTest2:[ccc, aaa]
        //4
        //unionAndStoreTest1:[ccc, aaa, ddd, bbb]

        /**
         * Long unionAndStore(K key, Collection<K> otherKeys, K destKey);
           key无序集合与多个otherkey无序集合的并集存储到destKey无序集合中
         */
        //使用：
        System.out.println("setTest:" + template.opsForSet().members("setTest"));
        System.out.println("setTest2:" + template.opsForSet().members("setTest2"));
        System.out.println("setTest3:" + template.opsForSet().members("setTest3"));
        List<String> strlist5 = new ArrayList<String>();
        strlist.add("setTest2");
        strlist.add("setTest3");
        System.out.println(template.opsForSet().unionAndStore("setTest",strlist5,"unionAndStoreTest2"));
        System.out.println("unionAndStoreTest2:" + template.opsForSet().members("unionAndStoreTest2"));
        //结果：setTest:[ddd, bbb, aaa, ccc]
        //setTest2:[ccc, aaa]
        //setTest3:[xxx, ccc, aaa]
        //5
        //unionAndStoreTest2:[ddd, xxx, bbb, aaa, ccc]

        /**
         * Set<V> difference(K key, K otherKey);
           key无序集合与otherKey无序集合的差集
         */
        //使用：
        System.out.println("setTest:" + template.opsForSet().members("setTest"));
        System.out.println("setTest2:" + template.opsForSet().members("setTest2"));
        System.out.println(template.opsForSet().difference("setTest","setTest2"));
        //结果：setTest:[ddd, bbb, aaa, ccc]
        //setTest2:[ccc, aaa]
        //[bbb, ddd]

        /**
         * Set<V> difference(K key, Collection<K> otherKeys);
           key无序集合与多个otherKey无序集合的差集
         */
        //使用：
        System.out.println("setTest:" + template.opsForSet().members("setTest"));
        System.out.println("setTest2:" + template.opsForSet().members("setTest2"));
        System.out.println("setTest3:" + template.opsForSet().members("setTest3"));
        List<String> strlist7 = new ArrayList<String>();
        strlist.add("setTest2");
        strlist.add("setTest3");
        System.out.println(template.opsForSet().difference("setTest",strlist7));
        //结果：setTest:[ddd, bbb, aaa, ccc]
        //setTest2:[ccc, aaa]
        //setTest3:[xxx, ccc, aaa]
        //[bbb, ddd]

        /**
         * Long differenceAndStore(K key, K otherKey, K destKey);
           key无序集合与otherkey无序集合的差集存储到destKey无序集合中
         */
        //使用：
        System.out.println("setTest:" + template.opsForSet().members("setTest"));
        System.out.println("setTest2:" + template.opsForSet().members("setTest2"));
        System.out.println(template.opsForSet().differenceAndStore("setTest","setTest2","differenceAndStore1"));
        System.out.println("differenceAndStore1:" + template.opsForSet().members("differenceAndStore1"));
        //结果：setTest:[ddd, bbb, aaa, ccc]
        //setTest2:[ccc, aaa]
        //2
        //differenceAndStore1:[bbb, ddd]

        /**
         * Long differenceAndStore(K key, Collection<K> otherKeys, K destKey);
           key无序集合与多个otherkey无序集合的差集存储到destKey无序集合中
         */

        //使用：
        System.out.println("setTest:" + template.opsForSet().members("setTest"));
        System.out.println("setTest2:" + template.opsForSet().members("setTest2"));
        System.out.println("setTest3:" + template.opsForSet().members("setTest3"));
        List<String> strlist8 = new ArrayList<String>();
        strlist.add("setTest2");
        strlist.add("setTest3");
        System.out.println(template.opsForSet().differenceAndStore("setTest",strlist8,"differenceAndStore2"));
        System.out.println("differenceAndStore2:" + template.opsForSet().members("differenceAndStore2"));
        //结果：setTest:[ddd, bbb, aaa, ccc]
        //setTest2:[ccc, aaa]
        //setTest3:[xxx, ccc, aaa]
        //2
        //differenceAndStore2:[bbb, ddd]

        /**
         * Set<V> members(K key);
           返回集合中的所有成员
         */
        //使用：
        System.out.println(template.opsForSet().members("setTest"));
        //结果：[ddd, bbb, aaa, ccc]

        /**
         * V randomMember(K key);
           随机获取key无序集合中的一个元素
         */
        //使用：
        System.out.println("setTest:" + template.opsForSet().members("setTest"));
        System.out.println("setTestrandomMember:" + template.opsForSet().randomMember("setTest"));
        System.out.println("setTestrandomMember:" + template.opsForSet().randomMember("setTest"));
        System.out.println("setTestrandomMember:" + template.opsForSet().randomMember("setTest"));
        System.out.println("setTestrandomMember:" + template.opsForSet().randomMember("setTest"));
        //结果：setTest:[ddd, bbb, aaa, ccc]
        //setTestrandomMember:aaa
        //setTestrandomMember:bbb
        //setTestrandomMember:aaa
        //setTestrandomMember:ddd

        /**
         * Set<V> distinctRandomMembers(K key, long count);
           获取多个key无序集合中的元素（去重），count表示个数
         */
        //使用：
        System.out.println("randomMembers:" + template.opsForSet().distinctRandomMembers("setTest",5));
        //结果：
        //randomMembers:[aaa, bbb, ddd, ccc]

        /**
         * List<V> randomMembers(K key, long count);
           获取多个key无序集合中的元素，count表示个数
         */
        //使用：
        System.out.println("randomMembers:" + template.opsForSet().randomMembers("setTest",5));
        //结果：randomMembers:[ccc, ddd, ddd, ddd, aaa]

        /**
         * Cursor<V> scan(K key, ScanOptions options);
           遍历set
         */
        //使用：
        Cursor<Object> curosr = template.opsForSet().scan("setTest", ScanOptions.NONE);
        while(curosr.hasNext()){
            System.out.println(curosr.next());
        }
        //结果：
        //ddd
        //bbb
        //aaa
        //ccc
    }
}



