package com.cn.springmvc.exception;

import com.sun.istack.internal.Nullable;

/**
 * Created by admin on 2018/8/8.
 */
public class BeansException extends RuntimeException {

    public BeansException(String msg) {
        super(msg);
    }

    public BeansException(@Nullable String msg, @Nullable Throwable cause) {
        super(msg, cause);
    }

}
