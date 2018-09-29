package com.cn.cacheable.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/6/11.
 */
@Controller
public class RedisListDemoController {

    @Autowired
    private RedisTemplate<String,Object> template;

    /**
     * public interface ListOperations<K,V>
       Redis列表是简单的字符串列表，按照插入顺序排序。
       你可以添加一个元素导列表的头部（左边）或者尾部（右边）
       ListOperations专门操作list列表：
     */

    @RequestMapping(value="/test/redis/list", method= RequestMethod.GET)
    @ResponseBody
    public void TestRedisList() {

        ValueOperations<String,Object> valueOperations = template.opsForValue();

        /**
         * Long leftPush(K key, V value);
         将所有指定的值插入存储在键的列表的头部。
         如果键不存在，则在执行推送操作之前将其创建为空列表。（从左边插入）
         */
        //使用：
        template.opsForList().leftPush("list","c#");
        template.opsForList().leftPush("list","c++");
        template.opsForList().leftPush("list","python");
        template.opsForList().leftPush("list","java");
        template.opsForList().leftPush("list","c#");
        template.opsForList().leftPush("list","c#");
        //结果:返回的结果为推送操作后的列表的长度
        //1
        //2
        //...


        /**
         * List<V> range(K key, long start, long end);
         * 返回存储在键中的列表的指定元素。偏移开始和停止是基于零的索引，其中0是列表的第一个元素（列表的头部），1是下一个元素
         */
        //使用：
        System.out.println(template.opsForList().range("list",0,-1));
        //结果:[c#, c++, python, java, c#, c#]


        /**
         * void trim(K key, long start, long end);
         * 修剪现有列表，使其只包含指定的指定范围的元素，起始和停止都是基于0的索引
         */
        //使用：
        System.out.println(template.opsForList().range("list",0,-1));
        template.opsForList().trim("list",1,-1);//裁剪第一个元素
        System.out.println(template.opsForList().range("list",0,-1));
        //结果:[c#, c++, python, java, c#, c#]
        //[c++, python, java, c#, c#]


        /**
         * Long size(K key);
         * 返回存储在键中的列表的长度。如果键不存在，则将其解释为空列表，并返回0。
         * 当key存储的值不是列表时返回错误。
         */
        //使用：
        System.out.println(template.opsForList().size("list"));
        //结果:6

        /**
         * Long leftPushAll(K key, V... values);
           批量把一个数组插入到列表中
         */
       // 使用：
        String[] stringArrays = new String[]{"1","2","3"};
        template.opsForList().leftPushAll("listArray",stringArrays);
        System.out.println(template.opsForList().range("listArray",0,-1));
        //结果:[3, 2, 1]

        /**
         * Long leftPushAll(K key, Collection<V> values);
           批量把一个集合插入到列表中
         */
        //使用：
        List<Object> strings = new ArrayList<Object>();
        strings.add("1");
        strings.add("2");
        strings.add("3");
        template.opsForList().leftPushAll("listcollection4", strings);
        System.out.println(template.opsForList().range("listcollection4",0,-1));
        //结果:[3, 2, 1]

        /**
         * Long leftPushIfPresent(K key, V value);
           只有存在key对应的列表才能将这个value值插入到key所对应的列表中
         */
        //使用：
        System.out.println(template.opsForList().leftPushIfPresent("leftPushIfPresent","aa"));
        System.out.println(template.opsForList().leftPushIfPresent("leftPushIfPresent","bb"));
        System.out.println("==========分割线===========");
        System.out.println(template.opsForList().leftPush("leftPushIfPresent","aa"));
        System.out.println(template.opsForList().leftPushIfPresent("leftPushIfPresent","bb"));
        /**
                  结果:
         0
         0
         ==========分割线===========
         1
         2
         */

        /**
         * Long leftPush(K key, V pivot, V value);
           把value值放到key对应列表中pivot值的左面，如果pivot值存在的话
         */
        //使用：
        template.opsForList().leftPush("list","java","oc");
        System.out.print(template.opsForList().range("list",0,-1));
        //结果：[c++, python, oc, java, c#, c#]

        /**
         * Long rightPush(K key, V value);
           将所有指定的值插入存储在键的列表的头部。
           如果键不存在，则在执行推送操作之前将其创建为空列表。（从右边插入）
         */
        //使用：
        template.opsForList().rightPush("listRight","java");
        template.opsForList().rightPush("listRight","python");
        template.opsForList().rightPush("listRight","c++");
        //结果:
        //1
        //2
        //3

        /**
         * Long rightPushAll(K key, V... values);
         */
        //使用：
        String[] stringArrays1 = new String[]{"1","2","3"};
        template.opsForList().rightPushAll("listArrayRight",stringArrays1);
        System.out.println(template.opsForList().range("listArrayRight",0,-1));
        //结果:[1, 2, 3]

        /**
         * Long rightPushAll(K key, Collection<V> values);
         */
        //使用：
        List<Object> strings1 = new ArrayList<Object>();
        strings.add("1");
        strings.add("2");
        strings.add("3");
        template.opsForList().rightPushAll("listCollectionRight", strings1);
        System.out.println(template.opsForList().range("listCollectionRight",0,-1));
        // 结果:[1, 2, 3]

        /**
         * Long rightPushIfPresent(K key, V value);
           只有存在key对应的列表才能将这个value值插入到key所对应的列表中
         */
        //使用：
        System.out.println(template.opsForList().rightPushIfPresent("rightPushIfPresent","aa"));
        System.out.println(template.opsForList().rightPushIfPresent("rightPushIfPresent","bb"));
        System.out.println("==========分割线===========");
        System.out.println(template.opsForList().rightPush("rightPushIfPresent","aa"));
        System.out.println(template.opsForList().rightPushIfPresent("rightPushIfPresent","bb"));
        /**
         *         结果:
         0
         0
         ==========分割线===========
         1
         2
         */

        /**
         * Long rightPush(K key, V pivot, V value);
           把value值放到key对应列表中pivot值的右面，如果pivot值存在的话
         */
        //使用：
        System.out.println(template.opsForList().range("listRight",0,-1));
        template.opsForList().rightPush("listRight","python","oc");
        System.out.println(template.opsForList().range("listRight",0,-1));
        //结果:[java, python, c++]
        //     [java, python, oc, c++]

        /**
         * void set(K key, long index, V value);
           在列表中index的位置设置value值
         */
        //使用：
        System.out.println(template.opsForList().range("listRight",0,-1));
        template.opsForList().set("listRight",1,"setValue");
        System.out.println(template.opsForList().range("listRight",0,-1));
        //结果:[java, python, oc, c++]
        //     [java, setValue, oc, c++]

        /**
         * Long remove(K key, long count, Object value);
         从存储在键中的列表中删除等于值的元素的第一个计数事件。
         计数参数以下列方式影响操作：
         count> 0：删除等于从头到尾移动的值的元素。
         count <0：删除等于从尾到头移动的值的元素。
         count = 0：删除等于value的所有元素。
         */
        //使用：
        System.out.println(template.opsForList().range("listRight",0,-1));
        template.opsForList().remove("listRight",1,"setValue");//将删除列表中存储的列表中第一次次出现的“setValue”。
        System.out.println(template.opsForList().range("listRight",0,-1));
        //结果:[java, setValue, oc, c++]
        //[java, oc, c++]

        /**
         * V index(K key, long index);
           根据下表获取列表中的值，下标是从0开始的
         */
        //使用：
        System.out.println(template.opsForList().range("listRight",0,-1));
        System.out.println(template.opsForList().index("listRight",2));
        //结果:[java, oc, c++]
        //c++

        /**
         * V leftPop(K key);
           弹出最左边的元素，弹出之后该值在列表中将不复存在
         */
        //使用：
        System.out.println(template.opsForList().range("list",0,-1));
        System.out.println(template.opsForList().leftPop("list"));
        System.out.println(template.opsForList().range("list",0,-1));
        /**
         *
         结果:
         [c++, python, oc, java, c#, c#]
         c++
         [python, oc, java, c#, c#]
         */


        /**
         * V leftPop(K key, long timeout, TimeUnit unit);
           移出并获取列表的第一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。
           使用：用法与 leftPop(K key);一样
         */


        /**
         * V rightPop(K key);
           弹出最右边的元素，弹出之后该值在列表中将不复存在
         */
        //使用：
        System.out.println(template.opsForList().range("list",0,-1));
        System.out.println(template.opsForList().rightPop("list"));
        System.out.println(template.opsForList().range("list",0,-1));
        /**
         结果:
         [python, oc, java, c#, c#]
         c#
         [python, oc, java, c#]
         */

        /**
         * V rightPopAndLeftPush(K sourceKey, K destinationKey);
           用于移除列表的最后一个元素，并将该元素添加到另一个列表并返回。
         */

        //使用：
        System.out.println(template.opsForList().range("list",0,-1));
        template.opsForList().rightPopAndLeftPush("list","rightPopAndLeftPush");
        System.out.println(template.opsForList().range("list",0,-1));
        System.out.println(template.opsForList().range("rightPopAndLeftPush",0,-1));
        /**
         结果:
         [oc, java,c#]
         [oc, java]
         [c#]
         */


        /**
         * V rightPopAndLeftPush(K sourceKey, K destinationKey, long timeout, TimeUnit unit);
           用于移除列表的最后一个元素，并将该元素添加到另一个列表并返回，
           如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。
           使用：用法与rightPopAndLeftPush(K sourceKey, K destinationKey)一样
         */
    }
}
