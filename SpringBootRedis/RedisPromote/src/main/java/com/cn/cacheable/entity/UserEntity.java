package com.cn.cacheable.entity;


import javax.persistence.*;
import java.io.Serializable;

/**
 * 测试用户基本信息
 * @author admin
 */
@Entity
@Table(name = "test_user_info")
public class UserEntity implements Serializable {

    @Id
    @Column(name = "ui_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(name = "ui_name")
    private String userName;

    @Column(name = "ui_password")
    private String userPassword;

    @Column(name = "ui_age")
    private Integer userAge;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Integer getUserAge() {
        return userAge;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userAge=" + userAge +
                '}';
    }
}
