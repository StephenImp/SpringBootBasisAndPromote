package com.cn.oauth;


public class NewUserNotFoundException
    extends RuntimeException

{

    public NewUserNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public NewUserNotFoundException(String msg) {
        super(msg);
    }
}
