package com.cn.bean;

/**
 * Created by admin on 2018/7/11.
 */
public class PersonBean {

    private String name;
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public PersonBean() {
    }

    public PersonBean(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
}
