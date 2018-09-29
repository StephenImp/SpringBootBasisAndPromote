package com.cn;

import com.cn.entity.Customer;
import com.cn.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MongodbApplication implements CommandLineRunner {

    @Autowired
    private CustomerRepository repository;

    static Logger logger = LoggerFactory.getLogger(MongodbApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MongodbApplication.class, args);
        logger.info("【【【【【SpringBoot整合Mongodb启动完成.】】】】】");
    }

    @Override
    public void run(String... strings) throws Exception {
        // 删除全部
        //repository.deleteAll();
        // 添加一条数据
        //repository.save(new Customer("Stephen", "Imp"));


        // 查询全部
        //logger.info(JSON.toJSONString(repository.findAll()));
    }
}
