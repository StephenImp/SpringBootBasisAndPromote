package com.cn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * security oauth2 整合的3个核心配置类
 *
 * 1.资源服务配置 ResourceServerConfiguration
 * 2.授权认证服务配置 AuthorizationServerConfiguration
 * 3.security 配置 SecurityConfiguration
 */
@SpringBootApplication
public class Springauth2Application {

	public static void main(String[] args) {
		SpringApplication.run(Springauth2Application.class, args);
	}
}
