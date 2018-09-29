package com.cn.cacheable.service;

import com.cn.cacheable.annotations.SingleUserCache;
import com.cn.cacheable.entity.UserEntity;
import com.cn.cacheable.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 用户业务逻辑
 */
@Service
public class UserService {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(UserService.class);
    /**
     * 用户持久化接口注入
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * 查询全部用户
     * @Cacheable
     * cacheNames 就是 value :表示这个Cacheable的名称
     * 这个从效果来看，存在redis中的key就是 user.service.all::SimpleKey []
     */
    @Cacheable(cacheNames = "user.service.all")
    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    /**
     * 查询用户详情
     * 根据userId进行缓存
     * 并且年龄大于20的数据不进行缓存
     * #result?.userAge，?代表返回值为null时不做判断处理
     *
     * cacheNames+userId  为存到redis中的键
     *
     * user.service.detail::1------>reids中的键
     *
     * @param userId
     * @return
     */
    @SingleUserCache
    public Optional<UserEntity> detail(Integer userId) {
        return userRepository.findById(userId);
    }

    /**
     * 删除用户
     * 配置@CacheEvict后会根据cacheName以及key删除redis内对应的缓存
     *
     * @param userId
     */
    @CacheEvict(cacheNames = "user.service.detail", key = "#userId")
    public void delete(Integer userId) {
        userRepository.deleteById(userId);
    }

    /**
     * 一次清空多个缓存
     *
     * @param firstUserId  第一个用户编号
     * @param secondUserId 第二个用户编号
     */
    @Caching(evict = {
            @CacheEvict(cacheNames = "user.service.detail", key = "#firstUserId"),
            @CacheEvict(cacheNames = "user.service.detail", key = "#secondUserId")
    })
    public void deleteTwo(Integer firstUserId, Integer secondUserId) {
        // 删除第一个用户
        userRepository.deleteById(firstUserId);
        // 删除第二个用户
        userRepository.deleteById(secondUserId);
    }

    /**
     * 更新用户基本信息
     * 根据参数进行更新用户信息
     * 配置@CachePut后会将方法返回值重新根据配置的cacheName以及key设置到redis内
     *
     * @param userId 用户编号
     * @param name   用户名称
     */
    @CachePut(cacheNames = "user.service.update", key = "#userId")
    public Optional<UserEntity> update(Integer userId, String name) {
        // 查询用户详情
        Optional<UserEntity> user = userRepository.findById(userId);
        user.get().setUserName(name);
        // 执行更新
        userRepository.save(user.get());
        // 返回用户基本信息 用于更新缓存
        return userRepository.findById(userId);
    }
}
