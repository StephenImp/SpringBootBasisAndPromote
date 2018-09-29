package com.cn.jpa;


import com.cn.myself.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityJPA extends JpaRepository<Authority, String> {
}
