package com.cn.TestIocDemo;

import com.cn.bean.Person;
import com.cn.config.MainConfigOfPropertyValues;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;


public class IOCTest_PropertyValue {
	static AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfPropertyValues.class);

	public static void main(String[] args) {

		printBeans(applicationContext);
		System.out.println("=============");

		Person person = (Person) applicationContext.getBean("person");
		System.out.println(person);


		ConfigurableEnvironment environment = applicationContext.getEnvironment();
		String property = environment.getProperty("person.nickName");
		System.out.println(property);
		applicationContext.close();
	}

	private static void printBeans(AnnotationConfigApplicationContext applicationContext){
		String[] definitionNames = applicationContext.getBeanDefinitionNames();
		for (String name : definitionNames) {
			System.out.println(name);
		}
	}

}
