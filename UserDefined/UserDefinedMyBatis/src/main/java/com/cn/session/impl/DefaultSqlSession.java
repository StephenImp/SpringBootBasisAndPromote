package com.cn.session.impl;

import com.cn.binding.MapperProxy;
import com.cn.config.Configuration;
import com.cn.config.MapperStatement;
import com.cn.executor.Executor;
import com.cn.executor.impl.DefaultExecutor;
import com.cn.session.SqlSession;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.List;

/**
 * DefaultSqlSession提供给开发人员访问的API
 * Excutor去实现访问数据库的操作。
 *
 * 1.
 */
public class DefaultSqlSession implements SqlSession {

    private Configuration conf;

    private Executor executor;

    public DefaultSqlSession(Configuration conf) {
        this.conf = conf;
        executor = new DefaultExecutor(conf);
    }

    @Override
    public <T> T selectOne(String var1) {
        return null;
    }

    @Override
    public <T> T selectOne(String statement, Object parameter) {

        List<Object> selectList = this.selectList(statement, parameter);
        if (selectList == null || selectList.size() == 0) {
            return null;
        }
        if (selectList.size() == 1) {
            return (T) selectList.get(0);
        } else {
            throw new RuntimeException("to many result");
        }

    }

    @Override
    public <E> List<E> selectList(String statement, Object parameter) {

        //根据查询语句的键值，找出对应的对象
        MapperStatement ms = conf.getMapperStatements().get(statement);
        return executor.query(ms, parameter);
    }


    @Override
    public Configuration getConfiguration() {
        return null;
    }

    /**
     * MyBatis动态的帮我们去实现实现类的过程
     * 从ibatis转成MyBatis
     * @param type
     * @param <T>
     * @return
     *
     * Proxy所有动态代理的父类
     */
    @Override
    public <T> T getMapper(Class<T> type) {

        //通过动态代理
        MapperProxy proxy = new MapperProxy(this);

        /**
         * 参数1：类的加载器
         * 参数2：实现的哪个接口
         * 参数3：
         */
        return (T) Proxy.newProxyInstance(type.getClassLoader(),new Class[]{type},proxy);
    }

}
