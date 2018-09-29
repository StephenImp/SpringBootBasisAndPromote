package com.cn.actual.service;

import com.cn.actual.constant.Constants;
import com.cn.actual.entity.TokenModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;


/**
 * 通过Redis存储和验证token的实现类
 * 
 * @author david.lu
 * @date 20170227
 */
@Service
public class TokenManagerService{

    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    public void setRedis(RedisTemplate redisTemplate) {
        // 泛型设置成Long后必须更改对应的序列化方案
        // redisTemplate.setKeySerializer(new JdkSerializationRedisSerializer());
        RedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(stringSerializer);
        this.redisTemplate = redisTemplate;
    }

    public TokenModel createToken(String userId) {
        // 使用uuid作为源token
        String token = UUID.randomUUID().toString().replace("-", "");
        TokenModel model = new TokenModel(userId, token);
        // 存储到redis并设置过期时间
        redisTemplate.boundValueOps(token).set(userId, Constants.TOKEN_EXPIRES_HOUR, TimeUnit.HOURS);
        return model;
    }

    public TokenModel getToken(String authentication) {
        if (authentication == null || authentication.length() == 0) {
            return null;
        }
        String[] param = authentication.split("_");
        if (param.length != 2) {
            return null;
        }
        // 使用userId和源token简单拼接成的token，可以增加加密措施
        String userId = param[0];
        String token = param[1];
        return new TokenModel(userId, token);
    }

    public TokenModel getUserInfoId(String token) {
        if (token == null || token.length() == 0) {
            return null;
        }
        BoundValueOperations<String, String> stringTemplate = redisTemplate.boundValueOps(token);
        String userId = stringTemplate.get();

        TokenModel tokenModel = new TokenModel(userId, token);

        return tokenModel;
    }

    public boolean checkToken(TokenModel model) {
        if (model == null) {
            return false;
        }
        String userId = redisTemplate.boundValueOps(model.getToken()).get();
        if (userId == null) {
            return false;
        }
        // 如果验证成功，说明此用户进行了一次有效操作，延长token的过期时间
        redisTemplate.boundValueOps(model.getToken()).expire(Constants.TOKEN_EXPIRES_HOUR, TimeUnit.HOURS);
        return true;
    }

    public void deleteToken(String token) {
        redisTemplate.delete(token);
    }

    public RedisTemplate<String, String> getRedis() {
        return redisTemplate;
    }
}
