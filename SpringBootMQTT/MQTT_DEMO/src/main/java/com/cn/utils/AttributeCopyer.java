package com.cn.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 类的属性复制工具类
 * @date 2017-6-3 下午5:08:15
 */
public class AttributeCopyer {
    /**
     * 复制父类的属性到子类
     * @param father
     * @param child
     */
    public static <F, C extends F> void copy(F father, C child) {
        Class<?> fatherClass = father.getClass();
        Field ff[] = fatherClass.getDeclaredFields();
        for(int i=0;i<ff.length;i++) {
            Field f = ff[i]; //取出每一个属性
            try {
                //通过父类get方法取出属性值
                Method mf = fatherClass.getMethod("get"+upperHeadChar(f.getName()));
                Object obj = mf.invoke(father);
                //获得set方法的名字让子类执行
                Method mc = fatherClass.getMethod("set"+upperHeadChar(f.getName()),f.getType());
                mc.invoke(child, obj);
            } catch (NoSuchMethodException e) {
                //System.out.println("字段"+f.getName()+"没有get或set方法");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 把父类克隆为子类
     * @param father
     * @param clazz
     */
    public static <F, C extends F> C clone(F father,Class<C> clazz) {
        C child = null;
        try {
            child = clazz.newInstance();
            copy(father, child);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return child;
    }

    /**
     * 首字母大写
     * @param in
     * @return
     */
    private static String upperHeadChar(String in){
        String head=in.substring(0,1);
        String out=head.toUpperCase()+in.substring(1,in.length());
        return out;
    }
}
