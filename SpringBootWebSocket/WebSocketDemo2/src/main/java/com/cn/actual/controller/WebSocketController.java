package com.cn.actual.controller;

import com.alibaba.fastjson.JSONObject;
import com.cn.actual.config.WebSocket;
import com.cn.actual.entity.TokenModel;
import com.cn.actual.entity.WebsocketMessage;
import com.cn.actual.service.TokenManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;

@Controller
public class WebSocketController {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketController.class);

    @Autowired
    private TokenManagerService tokenManagerService;

    @RequestMapping(value = "/welcome",method = RequestMethod.GET)
    public String testWebSocket(@RequestParam("message") String message) {
        try {
            sendWebSocket(message);
        } catch (Exception e) {
            logger.info("发送WebScoket异常！！！" + e);
        }
        return "websocket";
    }


    /**
     * 群发自定义消息
     * */
    private void sendWebSocket(String message) throws Exception {

        //模拟token
        String token = UUID.randomUUID().toString();
        //TokenManagerService tokenManagerService = ApiFactory.getBean(TokenManagerService.class);

        try {
            CopyOnWriteArraySet<WebSocket> webSocketSet = WebSocket.webSocketSet;
            logger.info("webSocketSet：" + webSocketSet);
            WebSocket webSocket = null;
            // 匹配到指定连接的tc
            for (WebSocket item : webSocketSet) {
                TokenModel model = tokenManagerService.getUserInfoId(item.getToken());
                logger.info("发送websocket消息：当前登录用户ID:  " + model.getUserId() + ",token：" + item.getToken());
                if (null == model || StringUtils.isEmpty(model.getUserId())) {
                    continue;
                }
                //String token = item.getToken();
                System.out.println(token);
                webSocket = item;
                logger.info("当前webSocket：" + webSocket);
                //logger.info("发送websocket消息的token：" + webSocket.getToken());
                WebsocketMessage websocketMessage = new WebsocketMessage();
                websocketMessage.setMessage(message);
                WebSocket.sendInfo(webSocket, JSONObject.toJSONString(websocketMessage));
            }

        } catch (IOException e) {
            logger.error("发送websocket通知失败", e);
        }
    }

}
