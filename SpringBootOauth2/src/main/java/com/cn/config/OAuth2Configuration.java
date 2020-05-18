package com.cn.config;

import com.cn.oauth.CustomAuthenticationEntryPoint;
import com.cn.oauth.CustomLogoutSuccessHandler;
import com.cn.oauth.MyAuthenticationFailHandler;
import com.cn.oauth.MyAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;



/**
 * 资源服务配置


 * https://www.jianshu.com/p/ded9dc32f550
 * https://www.jianshu.com/p/63115c71a590
 *
 * https://www.cnblogs.com/tqlin/p/11358470.html



 * spring security oauth2的认证思路
 *
 * client模式，没有用户的概念，直接与认证服务器交互，用配置中的客户端信息去申请accessToken，
 *          *  *      客户端有自己的client_id,client_secret对应于用户的 username,password，而客户端也拥有自己的authorities，
 *          *  *      当采取client模式认证时，对应的权限也就是客户端自己的authorities。
 *
 *    客户端模式（Client Credentials Grant）指客户端以自己的名义，而不是以用户的名义，向"服务提供商"进行认证。
 *    严格地说，客户端模式并不属于OAuth框架所要解决的问题。在这种模式中，用户直接向客户端注册，客户端以自己的名义要求"服务提供商"提供服务，其实不存在授权问题。

 *
 * password模式，自己本身有一套用户体系，在认证时需要带上自己的用户名和密码，以及客户端的client_id,client_secret。
 *      此时，accessToken所包含的权限是用户本身的权限，而不是客户端的权限。
 *
 *    密码模式（Resource Owner Password Credentials Grant）中，用户向客户端提供自己的用户名和密码。客户端使用这些信息，向"服务商提供商"索要授权。
 *
 *
 * 作者对于两种模式的理解便是，
 * 如果你的系统已经有了一套用户体系，每个用户也有了一定的权限，可以采用password模式；
 * 如果仅仅是接口的对接，不考虑用户，则可以使用client模式。
 *
 *
 *
 */
@Configuration
public class OAuth2Configuration {

    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

        private static final String DEMO_RESOURCE_ID = "order";

        /**
         * 因为整合SpringSecurity的缘故，我们需要配置登出时清空对应的access_token控制以及自定义401错误内容
         */
        @Autowired
        private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;


        /**
         * 登出控制清空access_token
         */
        @Autowired
        private CustomLogoutSuccessHandler customLogoutSuccessHandler;

        @Autowired
        private MyAuthenticationFailHandler myAuthenticationFailHandler;

        @Autowired
        private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) {
            resources.resourceId(DEMO_RESOURCE_ID).stateless(true);
        }


        @Override
        public void configure(HttpSecurity http) throws Exception {


            //表单登录 方式
            http.formLogin()
                    .loginPage("/authentication/require")
                    //登录需要经过的url请求
                    .loginProcessingUrl("/authentication/form")
                    .successHandler(myAuthenticationSuccessHandler)
                    .failureHandler(myAuthenticationFailHandler);



            /**
             * 在配置类中我们排除了对/hello公开地址拦截
             * 以及/secure下的所有地址都必须授权才可以访问。
             */

            http

                    .authorizeRequests()
                    .antMatchers("/oauth/token").permitAll()
                    .antMatchers("/secure/**","/redis/**")
                    .authenticated()//必须验证后才能访问


                    .and()
                    // Since we want the protected resources to be accessible in the UI as well we need
                    // session creation to be allowed (it's disabled by default in 2.0.6)
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                    .and()
                    .requestMatchers().anyRequest()
                    .and()
                    .anonymous()
                    .and()
                    .exceptionHandling()
                    .authenticationEntryPoint(customAuthenticationEntryPoint)
                    .and()
                    .logout()
                    .logoutUrl("/oauth/logout")
                    .logoutSuccessHandler(customLogoutSuccessHandler)

                    .and()
                    //关闭跨站请求防护
                    .csrf().disable();




        }

    }

}
