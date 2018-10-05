package com.cn.executor;

import com.cn.config.MapperStatement;

import java.util.List;

/**
 * Created by Administrator on 2018\9\12 0012.
 * MyBatis核心接口之一，定义了数据库操作最基本的方法，sqlSession的功能都是基于它来实现的
 */
public interface Executor {

    /**
     * 查询接口
     * ms 封装sql语句的MapperStatement对象
     * parameter 传入sql的参数
     * return  将数据转换成指定对象结果集返回
     */

    <E>List<E> query(MapperStatement ms, Object parameter);

}
