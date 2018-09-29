package com.cn.binding;

import com.cn.session.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Collection;

/**
 * Created by Administrator on 2018\9\12 0012.
 */
public class MapperProxy implements InvocationHandler {

    private SqlSession session;

    public MapperProxy(SqlSession session) {
        this.session = session;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Class<?> returnType = method.getReturnType();

        //判断returnType是否是Collection的子类
        if (Collection.class.isAssignableFrom(returnType)) {
            return session.selectList(method.getDeclaringClass().getName() + "." + method.getName(), args == null ? null : args[0]);
        }else {
            return session.selectOne(method.getDeclaringClass().getName() + "." + method.getName(), args == null ? null : args[0]);
        }
    }
}
