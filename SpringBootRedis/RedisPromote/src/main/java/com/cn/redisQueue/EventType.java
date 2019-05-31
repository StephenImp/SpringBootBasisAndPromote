package com.cn.redisQueue;

/**
 * Created by MOZi on 2019/3/22.
 * 定义事件的类型
 */
public enum  EventType {

    CAlCULATE(0),
    COPYFILE(1),
    MAIL(2);

    private int value;
    public int getValue(){
        return value;
    }

    EventType(int value){
        this.value = value;
    }
}
