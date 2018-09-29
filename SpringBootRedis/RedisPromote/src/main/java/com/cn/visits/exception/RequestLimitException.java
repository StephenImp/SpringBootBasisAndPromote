package com.cn.visits.exception;

/**
 * Created by admin on 2018/6/11.
 */
public class RequestLimitException extends Exception {

    public RequestLimitException(){
        super("HTTP请求超出设定的限制");
    }

    public RequestLimitException(String message){
        super(message);
    }
}
