package com.cn.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Created by admin on 2018/7/12.
 */
@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insert(){
        String sql = "insert INTO t_user(t_name,t_age) VALUES(?,?)";

        String userName = UUID.randomUUID().toString().substring(0,5);
        jdbcTemplate.update(sql,userName,19);
    }
}
