package com.cn.demo.entity;

/**
 * 服务端向浏览器发送的实体
 */
public class WiselyReponse {
    private String responseMessage;

    public WiselyReponse(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
}
