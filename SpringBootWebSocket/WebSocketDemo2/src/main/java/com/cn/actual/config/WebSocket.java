package com.cn.actual.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/websocket/{Token}")
@Component
public class WebSocket {
    private static final Logger logger = LoggerFactory.getLogger(WebSocket.class);
    // 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    // concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    public static CopyOnWriteArraySet<WebSocket> webSocketSet = new CopyOnWriteArraySet<WebSocket>();

    // 与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    // 登录用户
    private String token;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(@PathParam("Token") String token, Session session) {
        this.token = token;
        this.session = session;
        webSocketSet.add(this); // 加入set中
        addOnlineCount(); // 在线数加1
        logger.info("有新连接加入！新加入的token为：" + this.token + "，当前在线人数为" + getOnlineCount());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this); // 从set中删除
        subOnlineCount(); // 在线数减1
        logger.info("有一连接关闭！关闭的token为：" + this.token + "，当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        // 群发消息
        for (WebSocket item : webSocketSet) {
            try {
                logger.error("客户端发送消：{}", message);
                item.sendMessage(message);
            } catch (IOException e) {
                logger.error("客户端发送消息异常：{}", e.getMessage());
            }
        }
    }

    /**
     * 发生错误时调用
     * 
     * 
     */
    @OnError
    public void onError(Session session, Throwable error) {
        logger.error("错误时调用：{}", error.getMessage());
    }

    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message); // this.session.getAsyncRemote().sendText(message);

    }

    /**
     * 
     *
     * 功能描述：给连接的客户端发送信息
     * 
     * @param item
     * @param message
     * @throws IOException void
     *
     */
    public static void sendInfo(WebSocket item, String message) throws IOException {
        try {
            item.sendMessage(message);
            logger.info("当前推送token为：" + item.getToken());
        } catch (IOException e) {

        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocket.onlineCount--;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
