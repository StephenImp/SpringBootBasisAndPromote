package com.cn;

import com.cn.client.MqttServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages={"com.cn"})
public class MqttDemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(MqttDemoApplication.class, args);
	}

	@Autowired
	private MqttServer mqttServer;

	@Override
	public void run(String... strings) throws Exception {
		mqttServer.connect();
	}

}
