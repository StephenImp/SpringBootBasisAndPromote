package com.cn.demo.controller;

import com.cn.demo.entity.WiselyMessage;
import com.cn.demo.entity.WiselyReponse;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WsController {
    /**
     * @MessageMapping注解开启WebSocket的访问地址映射
     * 当浏览器向服务端发送请求时，通过@MessageMapping映射/welcome这个地址，
     * 类似@RequestMapping，当服务端有消息存在时，会对订阅@SendTo中路径的浏览器发送请求。
     */
    @MessageMapping("/welcome")//前端请求地址
    @SendTo("/topic/getResponse")//后台返回给前端的地址
    public WiselyReponse say(WiselyMessage message) throws Exception
    {

        System.out.println(message.toString());
        //等待3秒返回消息内容
        Thread.sleep(3000);
        return new WiselyReponse("欢迎使用webScoket："+message.getName());
    }

}
