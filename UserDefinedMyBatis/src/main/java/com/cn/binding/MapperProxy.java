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

    /**
     * proxy:代表动态代理对象
     * method：代表正在执行的方法
     * args：代表调用目标方法时传入的实参
     */
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

    public MapperProxy(SqlSession session) {
        this.session = session;
    }
}
