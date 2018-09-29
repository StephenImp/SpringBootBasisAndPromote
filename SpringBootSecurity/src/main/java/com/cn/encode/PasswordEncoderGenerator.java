package com.cn.encode;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by admin on 2018/4/11.
 */
public class PasswordEncoderGenerator {

    public static void main(String[] args) {
        String password = "123456";
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        System.out.println(hashedPassword);
    }
}
