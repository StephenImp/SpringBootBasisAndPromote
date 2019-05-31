package com.cn.redisQueue;

import java.util.List;

/**
 * Created by MOZi on 2019/3/22.
 * 处理事件的接口
 */
public interface  EventHandler {
    //处理事件
    void doHandler(EventModel eventModel);
    //获取关注事件的类型
    List<EventType> getSupportEventType();
}
