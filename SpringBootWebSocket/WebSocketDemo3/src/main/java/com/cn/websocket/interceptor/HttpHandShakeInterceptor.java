package com.cn.websocket.interceptor;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class HttpHandShakeInterceptor implements HandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        System.out.println("【HttpHandShakeInterceptor-beforeHandshake方法】");
        ServletServerHttpRequest httpRequest = (ServletServerHttpRequest) request;
        HttpServletRequest servletRequest = httpRequest.getServletRequest();
        String sessionID = servletRequest.getSession().getId();
        System.out.println("【SessionId】------>"+sessionID);
        attributes.put("sessionID", sessionID);

//        boolean flag = false;

//        Cookie[] cookies = servletRequest.getCookies();
//        if (null != cookies) {
//            for (Cookie cookie : cookies) {
//                if ("uid".equals(cookie.getName())){
//                    flag = true;
//                }
//            }
//        }

        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {
        System.out.println("【HttpHandShakeInterceptor-afterHandshake方法】");
    }
}
