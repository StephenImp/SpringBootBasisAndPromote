package com.cn.TestIocDemo;

import com.cn.bean.Boss;
import com.cn.bean.Car;
import com.cn.bean.Color;
import com.cn.config.MainConifgOfAutowired;
import com.cn.dao.BookDao;
import com.cn.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class IOCTest_Autowired {

	public static void main(String[] args) {

		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConifgOfAutowired.class);

		/**
		 * 用来测试@Autowired @Resource  @Inject 标注在属性上
		 */
		BookService bookService = applicationContext.getBean(BookService.class);
		System.out.println(bookService);

		BookDao bean = applicationContext.getBean(BookDao.class);
		System.out.println(bean);

		/**
		 *@Autowired
		  标注在方法，Spring容器创建当前对象，就会调用方法，完成赋值；
		  方法使用的参数，自定义类型的值从ioc容器中获取

		  默认加在ioc容器中的组件，容器启动会调用无参构造器创建对象，再进行初始化赋值等操作

		 @Autowired 标注在有参构造器上
		 有参构造器中需要的属性，也是从ioc容器中获取的

		 */
		Boss boss = applicationContext.getBean(Boss.class);
		System.out.println(boss);
		Car car = applicationContext.getBean(Car.class);
		System.out.println(car);


		/**
		 * @Bean标注的方法创建对象的时候，方法参数的值从容器中获取
		 */

		Color color = applicationContext.getBean(Color.class);
		System.out.println(color);
		System.out.println("@@@@@@@@@@@@@"+applicationContext);
		applicationContext.close();

	}

}
