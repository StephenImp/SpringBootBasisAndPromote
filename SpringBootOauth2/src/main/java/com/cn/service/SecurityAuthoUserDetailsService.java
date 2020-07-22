package com.cn.service;

import com.cn.entity.Authority;
import com.cn.entity.User;
import com.cn.jpa.UserJPA;
import com.cn.oauth.NewUserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

@Component("userDetailsService")
public class SecurityAuthoUserDetailsService implements UserDetailsService

        /**
         * 从数据库读取用户的操作，如果没有查询到用户直接抛出异常提示，
         * 如果查询到并且设置对应的角色后返回SpringSecurity内置的User对象实例。
         */
{
    @Autowired
    private UserJPA userRepository;

    /**
     * 实现UserDetailsService中的loadUserByUsername方法，用于加载用户数据
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {

        String lowercaseLogin = login.toLowerCase();

        User userFromDatabase = userRepository.findByUsernameCaseInsensitive(lowercaseLogin);

        if (userFromDatabase == null) {
            throw new NewUserNotFoundException("User " + lowercaseLogin + " was not found in the database");
        }

        //用户权限列表
        //获取用户的所有权限并且SpringSecurity需要的集合
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Authority authority : userFromDatabase.getAuthorities()) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority.getName());
            grantedAuthorities.add(grantedAuthority);
        }

        //返回一个SpringSecurity需要的用户对象
        return new org.springframework.security.core.userdetails.User(
                userFromDatabase.getUsername(),
                userFromDatabase.getPassword(),
                grantedAuthorities);

    }
}
