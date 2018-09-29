package com.cn.demo.entity;

/**
 * 用于浏览器向服务端发送消息参数
 */
public class WiselyMessage {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "WiselyMessage{" +
                "name='" + name + '\'' +
                '}';
    }
}
