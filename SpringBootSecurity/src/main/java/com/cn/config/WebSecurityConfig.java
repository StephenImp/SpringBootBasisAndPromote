package com.cn.config;

import com.cn.service.UserSecurityService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)// 控制权限注解
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
    //完成自定义认证实体注入
    @Bean
    UserDetailsService userService()
    {
        UserSecurityService userService = new UserSecurityService();
        return userService;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
         当前版本5新增支持加密方式：
         bcrypt - BCryptPasswordEncoder (Also used for encoding)
         ldap - LdapShaPasswordEncoder
         MD4 - Md4PasswordEncoder
         MD5 - new MessageDigestPasswordEncoder("MD5")
         noop - NoOpPasswordEncoder
         pbkdf2 - Pbkdf2PasswordEncoder
         scrypt - SCryptPasswordEncoder
         SHA-1 - new MessageDigestPasswordEncoder("SHA-1")
         SHA-256 - new MessageDigestPasswordEncoder("SHA-256")
         sha256 - StandardPasswordEncoder
     */

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService()).passwordEncoder(passwordEncoder());
    }

    /**
     * configureGlobal(AuthenticationManagerBuilder auth) 方法，
     * 在内存中创建了一个用户，该用户的名称为username，密码为password，用户角色为USER。
     */
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .inMemoryAuthentication()
//                .passwordEncoder(new MyPasswordEncoder())//在此处应用自定义PasswordEncoder
//                .withUser("admin")
//                .password("123456")
//                .roles("USER");
//    }

    /**
     * 通过 authorizeRequests() 定义哪些URL需要被保护、哪些不需要被保护。
     * 例如以上代码指定了 / 和 /home 不需要任何认证就可以访问，其他的路径都必须通过身份验证。
     *
     * 通过 formLogin() 定义当需要用户登录时候，转到的登录页面。
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/hello","/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        //解决静态资源被拦截的问题
//        web.ignoring().antMatchers("/css/**");
//    }


}
