package com.cn.jpa;


import com.cn.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityJPA extends JpaRepository<Authority, String> {
}
