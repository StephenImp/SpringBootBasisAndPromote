package com.cn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;

import java.io.File;

@SpringBootApplication
public class WebsocketApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebsocketApplication.class, args);
	}

	//https://blog.csdn.net/weixin_39715061/article/details/83025643
	//改成聚合工程后，找不到 jsp文件
	@Bean
	public WebServerFactoryCustomizer<TomcatServletWebServerFactory> customizer() {
		return (factory) -> {
			factory.addContextCustomizers((context) -> {
						//当然这里要写你自己的
						String relativePath = "E:/workspace/myself/SpringBootBasisAndPromote/SpringBootWebSocket/WebSocketDemo1/src/main/webapp";
						// 相对于 user.dir = D:\javawork\dive-in-spring-boot
						File docBaseFile = new File(relativePath);
						// 路径是否存在
						if (docBaseFile.exists()) {
							context.setDocBase(docBaseFile.getAbsolutePath());
						}
					}
			);
		};
	}

}
