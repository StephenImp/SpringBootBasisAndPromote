package com.cn.starter.service;


/**
 * 注意这里的service没有被扫到spring容器中.
 */
//@Service
public class HelloService {
    //消息内容
    private String msg;
    //是否显示消息内容
    private boolean show = true;

    public String sayHello() {
        return show ? "Hello，" + msg : "Hidden";
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setShow(boolean show) {
        this.show = show;
    }
}
