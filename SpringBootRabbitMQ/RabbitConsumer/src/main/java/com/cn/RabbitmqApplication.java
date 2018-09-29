package com.cn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RabbitmqApplication {


	static Logger logger = LoggerFactory.getLogger(RabbitmqApplication.class);

	/**
	 * rabbitmq消费者启动入口
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(RabbitmqApplication.class, args);

		logger.info("【【【【【消息队列-消息消费者启动成功.】】】】】");
	}
}
