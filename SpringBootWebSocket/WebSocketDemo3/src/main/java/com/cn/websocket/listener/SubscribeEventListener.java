package com.cn.websocket.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.util.Objects;

@Component
public class SubscribeEventListener implements ApplicationListener<SessionSubscribeEvent> {
    /**
     * 订阅消息触发事件，调用这个方法
     * StompHeaderAccessor：简单消息传递协议中处理消息头的基类
     *     通过这个类，可以获取消息类型(例如：发布订阅，建立连接断开连接), 会话id等
     */
    @Override
    public void onApplicationEvent(SessionSubscribeEvent sessionSubscribeEvent) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(sessionSubscribeEvent.getMessage());
        System.out.println("【SubscribeEventListener订阅事件监听器】---->"+headerAccessor.getMessageType());
        System.out.println("【SubscribeEventListener订阅事件监听器: SessionID】---->"+ Objects.requireNonNull(headerAccessor.
                getSessionAttributes()).get("sessionID"));
    }

}
