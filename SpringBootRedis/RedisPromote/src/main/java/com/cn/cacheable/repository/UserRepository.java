package com.cn.cacheable.repository;

import com.cn.cacheable.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<UserEntity, Integer> {
}
