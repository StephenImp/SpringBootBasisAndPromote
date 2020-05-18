package com.cn.config;


import com.cn.oauth.Authorities;
import com.cn.service.SecurityAuthoUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;

/**
 *
 * 认证授权配置AuthorizationServerConfigurerAdapter
 * 开启OAuth2验证服务器
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter implements EnvironmentAware {

    private static final String ENV_OAUTH = "authentication.oauth.";
    private static final String PROP_CLIENT_MODE = "client_id";
    private static final String PROP_PASSWORD_MODE = "password_id";
    private static final String PROP_SECRET = "secret";
    private static final String PROP_TOKEN_VALIDITY_SECONDS = "tokenValidityInSeconds";

    private static final String DEMO_RESOURCE_ID = "order";

    private RelaxedPropertyResolver propertyResolver;

    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;

    //自定义UserDetailsService注入
    @Autowired
    private SecurityAuthoUserDetailsService userDetailsService;

    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Autowired
    RedisConnectionFactory redisConnectionFactory;


    /**
     *  * client模式，没有用户的概念，直接与认证服务器交互，用配置中的客户端信息去申请accessToken，
     *  *      客户端有自己的client_id,client_secret对应于用户的 username,password，而客户端也拥有自己的authorities，
     *  *      当采取client模式认证时，对应的权限也就是客户端自己的authorities。
     *
     *
     *  * password模式，自己本身有一套用户体系，在认证时需要带上自己的用户名和密码，以及客户端的client_id,client_secret。
     *    此时，accessToken所包含的权限是用户本身的权限，而不是客户端的权限。
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                .inMemory()

                //client模式
                .withClient(propertyResolver.getProperty(PROP_CLIENT_MODE))
                .resourceIds(DEMO_RESOURCE_ID)
                .authorizedGrantTypes("client_credentials", "refresh_token")
                .scopes("select")
                .authorities("oauth2")
                .secret(propertyResolver.getProperty(PROP_SECRET))


                .and()

                //密码模式
                .withClient(propertyResolver.getProperty(PROP_PASSWORD_MODE))
                .resourceIds(DEMO_RESOURCE_ID)
                .authorizedGrantTypes("password", "refresh_token")
                .scopes("read", "write")
                .authorities(Authorities.ROLE_ADMIN.name(), Authorities.ROLE_USER.name())
                .secret(propertyResolver.getProperty(PROP_SECRET))
                .accessTokenValiditySeconds(propertyResolver.getProperty(PROP_TOKEN_VALIDITY_SECONDS, Integer.class, 1800));
    }

        /**
         *
         * 认证服务端点配置
         * token放在redis里面
         *
         * @throws Exception
         */
        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints
                    //用户管理
                    .userDetailsService(userDetailsService)
                    //设置令牌  token存到redis
                    .tokenStore(new RedisTokenStore(redisConnectionFactory))
                    //启用oauth2管理
                    .authenticationManager(authenticationManager)
                    //接收GET和POST
                    .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);

            // 最后一个参数为替换之后授权页面的url   这个有什么用？
            //endpoints.pathMapping("/oauth/confirm_access","/custom/confirm_access");

//            DefaultTokenServices tokenServices = new DefaultTokenServices();
//            tokenServices.setTokenStore(endpoints.getTokenStore());
//            tokenServices.setSupportRefreshToken(true);
//            tokenServices.setClientDetailsService(endpoints.getClientDetailsService());
//            tokenServices.setTokenEnhancer(endpoints.getTokenEnhancer());
//            tokenServices.setAccessTokenValiditySeconds(60*60*2);//token有效期设置2个小时
//            tokenServices.setRefreshTokenValiditySeconds(60*60*12);//Refresh_token:12个小时
//            endpoints.tokenServices(tokenServices);


        }


    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        //允许表单认证
        oauthServer.allowFormAuthenticationForClients();
    }



    @Override
    public void setEnvironment(Environment environment) {
        this.propertyResolver = new RelaxedPropertyResolver(environment, ENV_OAUTH);
    }
}
