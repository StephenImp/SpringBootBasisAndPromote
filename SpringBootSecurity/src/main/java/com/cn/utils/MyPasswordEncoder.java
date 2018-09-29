package com.cn.utils;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by admin on 2018/4/11.
 */
public class MyPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence arg0) {
        return arg0.toString();
    }

    @Override
    public boolean matches(CharSequence arg0, String arg1) {
        return arg1.equals(arg0.toString());
    }
}
