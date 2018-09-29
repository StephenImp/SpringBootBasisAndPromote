package com.cn.quartz.jpa;

import com.cn.quartz.entity.GoodInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodInfoRepository
    extends JpaRepository<GoodInfoEntity,Long>
{
}
