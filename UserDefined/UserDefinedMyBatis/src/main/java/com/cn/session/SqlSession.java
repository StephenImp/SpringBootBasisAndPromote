package com.cn.session;

import com.cn.config.Configuration;

import java.sql.Connection;
import java.util.List;

/**
 * Created by Administrator on 2018\9\12 0012.
 * 是MyBatis对外访问最主要的API
 * 实际上的SqlSession的功能都是基于Excutor来实现的,对于Mybatis来说，真正访问数据的对象是Excutor对象。
 */
public interface SqlSession {

    //泛型方法，经过查询返回一个指定的对象
    <T> T selectOne(String statement);

    //根据传入的条件查询对象
    <T> T selectOne(String statement, Object parameter);

    <E> List<E> selectList(String statement, Object parameter);

    Configuration getConfiguration();

    <T> T getMapper(Class<T> type);

}
