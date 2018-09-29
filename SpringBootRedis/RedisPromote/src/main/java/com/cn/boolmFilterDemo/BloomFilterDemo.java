package com.cn.boolmFilterDemo;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.junit.Test;

import java.nio.charset.Charset;
import java.util.*;


/**
 * Created by admin on 2018/8/23.
 * <p>
 * 场景：从100W的 数据量中查找一个人
 *
 * 重点：①hash函数的个数
 *       ②bit数组的长度
 */
public class BloomFilterDemo {

    private static final int insertions = 1000000;

    @Test
    public void bfTest() {

        /**
         * ①
         * 初始化一个存储String类型数据的布隆过滤器，初始化大小为100W
         * BloomFilter中存的都是Funnels
         *
         * Funnels这里使用的是适配器模式。
         *
         * fpp:误判率
         */
        BloomFilter<String> bf = BloomFilter.create(Funnels.stringFunnel(Charset.defaultCharset()), insertions, 0.001);

        /**
         * 初始化一个存储String数据的set,初始化大小为100W
         */
        Set<String> sets = new HashSet<>(insertions);

        /**
         * 初始化一个存储String数据的List,初始化大小为100W
         */
        List<String> lists = new ArrayList<>(insertions);

        /**
         * 向三个容器初始化100W个随机并且唯一的字符串
         */
        for (int i = 0; i < insertions; i++) {
            String uuid = UUID.randomUUID().toString();
            bf.put(uuid);//②
            sets.add(uuid);
            lists.add(uuid);
        }

        int right = 0;//布隆过滤器正确的判断次数
        int wrong = 0;//布隆过滤器错误的判断次数

        for (int i = 0; i < 10000; i++) {
            String test = i % 100 == 0 ? lists.get(i / 100) : UUID.randomUUID().toString();//按照一定的比例选择bf中肯定存在的值
            if (bf.mightContain(test)) {//布隆过滤器判断test是否存在 ③
                if (sets.contains(test)) {
                    right++;
                } else {
                    wrong++;
                }
            }
        }

        System.out.println("===========right============"+right);
        System.out.println("===========wrong============"+wrong);

    }
}
