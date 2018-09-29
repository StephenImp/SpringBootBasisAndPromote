package com.cn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 消息消费者程序启动入口

 */
@SpringBootApplication
@ComponentScan(value = "com.cn")
public class TopicconsumerApplication {

	/**
	 * logback
	 */
	private static Logger logger = LoggerFactory.getLogger(TopicconsumerApplication.class);

	/**
	 * 程序入口
	 * @param args
	 */
	public static void main(String[] args)
	{
		SpringApplication.run(TopicconsumerApplication.class,args);

		logger.info("【【【【【Topic队列消息Consumer启动成功】】】】】");
	}
}
