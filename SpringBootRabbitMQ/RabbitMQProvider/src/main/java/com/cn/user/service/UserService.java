package com.cn.user.service;

import com.cn.enums.ExchangeEnum;
import com.cn.enums.QueueEnum;
import com.cn.rabbitmq.QueueMessageService;
import com.cn.user.entity.UserEntity;
import com.cn.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserService {
    @Autowired
    private UserRepository userRepository;
    /**
     * 消息队列业务逻辑实现
     */
    @Autowired
    private QueueMessageService queueMessageService;

    /**
     * 保存用户
     * 并写入消息队列
     *
     * @param userEntity
     * @return
     */
    public Long save(UserEntity userEntity) throws Exception {
        /**
         * 保存用户
         */
        userRepository.save(userEntity);

        /**
         * 将消息写入消息队列
         */
        queueMessageService.send(userEntity.getId(), ExchangeEnum.USER_REGISTER, QueueEnum.USER_REGISTER);

        return userEntity.getId();
    }

    /**
     * 查询单个用户
     * @return
     */
    public UserEntity findOne(Long id){

        /**
         * 将消息写入消息队列
         */
        try {
            queueMessageService.send(id, ExchangeEnum.USER_REGISTER, QueueEnum.USER_REGISTER);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userRepository.findOne(id);

    }
}
