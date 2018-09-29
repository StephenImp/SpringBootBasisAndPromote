package com.cn.user.repository;

import com.cn.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 用户数据接口定义
 */
public interface UserRepository
    extends JpaRepository<UserEntity,Long>
{
}
