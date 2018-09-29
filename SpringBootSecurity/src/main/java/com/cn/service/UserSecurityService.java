package com.cn.service;

import com.cn.entity.security.RoleEntity;
import com.cn.entity.security.UserSecurityEntity;
import com.cn.jpa.security.UserSecurityJPA;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;


public class UserSecurityService implements UserDetailsService
{
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(UserDetailsService.class);

    @Autowired
    UserSecurityJPA userJPA;

    @Override
    //重写loadUserByUsername 方法获得 userdetails 类型用户
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserSecurityEntity user = userJPA.findByUsername(username);
        if(user == null)
        {
            throw new UsernameNotFoundException("未查询到用户："+username+"信息！");
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        //用于添加用户的权限。只要把用户权限添加到authorities 就万事大吉。
        for(RoleEntity role:user.getRoles())
        {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
            logger.info("flag: " + role.getName());
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), authorities);
    }
}
