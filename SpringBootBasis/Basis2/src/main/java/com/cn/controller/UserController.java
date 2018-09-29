package com.cn.controller;

import com.cn.entity.UserEntity;
import com.cn.jpa.UserJPA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(value = "/user")
public class UserController {
    /**
     * LogBack读取配置文件的步骤
     * （1）尝试classpath下查找文件logback-test.xml
     * （2）如果文件不存在，尝试查找logback.xml
     * （3）如果两个文件都不存在，LogBack用BasicConfiguration自动对自己进行最小化配置，这样既实现了上面我们不需要添加任何配置就可以输出到控制台日志信息。
     * <p>
     */
    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserJPA userJPA;

    /**
     * 查询用户列表方法
     *
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<UserEntity> list() {
        return userJPA.findAll();
    }

    /**
     * 添加、更新用户方法
     *
     * @param entity
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.GET)
    public UserEntity save(UserEntity entity) {
        return userJPA.save(entity);
    }

    /**
     * 删除用户方法
     *
     * @param id 用户编号
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public List<UserEntity> delete(Long id) {
        UserEntity user = new UserEntity();
        user.setId(id);
        userJPA.delete(user);
        return userJPA.findAll();
    }

    /**
     * 访问首页
     *
     * @return
     */
    @RequestMapping(value = "/testLogger")
    public String index() {
        logger.debug("记录debug日志");
        logger.info("访问了index方法");
        logger.error("记录error错误日志");
        return "index";
    }
}


