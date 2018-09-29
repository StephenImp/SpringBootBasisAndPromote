Security

访问localhost:8080/xx  任何一个地址都会被拦截  在WebSecurityConfig中配置
核心
1.MVCConfig
2.WebSecurityConfig
3.在UserDetailsService中查询账户,密码,以及权限,并存入org.springframework.security.core.userdetails.User,来验证

4.thymeleaf模板用security标签 引入jar包
		<dependency>
			<groupId>org.thymeleaf.extras</groupId>
			<artifactId>thymeleaf-extras-springsecurity4</artifactId>
		</dependency>



参考博客https://www.jianshu.com/p/9a08417e4e84

本工程主要是Spring Security的使用