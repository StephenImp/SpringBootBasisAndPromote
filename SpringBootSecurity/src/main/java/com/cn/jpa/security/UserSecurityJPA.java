package com.cn.jpa.security;

import com.cn.entity.security.UserSecurityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSecurityJPA extends JpaRepository<UserSecurityEntity,Long>
{
    //使用SpringDataJPA方法定义查询
    public UserSecurityEntity findByUsername(String username);
}
