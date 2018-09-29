package com.cn.jpa;

import com.cn.entity.LoggerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 请求日志数据接口
 */
public interface LoggerJPA
        extends JpaRepository<LoggerEntity,Long>
{

}
