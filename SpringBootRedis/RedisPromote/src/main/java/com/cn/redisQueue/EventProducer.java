package com.cn.redisQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by MOZi on 2019/3/22.
 * 将事件发送到工作队列中
 */
public class EventProducer {

    private static final Logger logger = LoggerFactory.getLogger(EventProducer.class);

    @Autowired
    JedisAdapter jedisAdapter;

    public boolean fireEvent(EventModel eventModel){
        try {
            //序列化
            String json = JSONObject.toJSONString(eventModel);
            //产生key
            String key = RedisKeyUtil.getEventQueueKey();
            //放入工作队列中
            jedisAdapter.lpush(key, json);
            return true;
        }catch (Exception e){
            logger.error("EventProducer fireEvent 异常 ：" + e.getMessage());
            return false;
        }

    }

}
