package com.cn.bean;

import org.springframework.beans.factory.annotation.Value;

public class Person {


    //使用@Value赋值；
    //1、基本数值
    //2、可以写SpEL； #{}
    //3、可以写${}；取出配置文件【properties】中的值（在运行环境变量里面的值）


    @Value("张三")
    private String name;
    @Value("#{20-2}")
    private Integer age;

    @Value("#{'Spring Expression Language'}") //获取字符串常量
    private String str;


    @Value("${person.nickName}")
    private String nickName;


    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

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

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public Person() {
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", str='" + str + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}
