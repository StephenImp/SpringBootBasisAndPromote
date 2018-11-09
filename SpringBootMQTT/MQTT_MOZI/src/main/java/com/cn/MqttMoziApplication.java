package com.cn;

import com.cn.server.MqttServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages={"com.cn"})
@SpringBootApplication
public class MqttMoziApplication implements CommandLineRunner {

	@Autowired
	private MqttServer mqttServer;

	public static void main(String[] args) {
		SpringApplication.run(MqttMoziApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		mqttServer.connect();
	}
}
