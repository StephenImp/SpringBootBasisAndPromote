package com.cn.eventandlistener.bean;

import lombok.Data;

@Data
public class UserBean {
    //用户名
    private String name;
    //密码
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
