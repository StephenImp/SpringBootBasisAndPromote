package com.cn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class RabbitmqproviderApplication {

	static Logger logger = LoggerFactory.getLogger(RabbitmqproviderApplication.class);

	/**
	 * 消息队列提供者启动入口
	 * @param args
	 */
	public static void main(String[] args)
	{
		SpringApplication.run(RabbitmqproviderApplication.class,args);

		logger.info("【【【【【消息队列-消息提供者启动成功.】】】】】");
	}

}
