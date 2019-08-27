package com.cn.quartz_2.jpa;

import com.cn.quartz_2.entity.GoodInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodInfoRepository
    extends JpaRepository<GoodInfoEntity,Long>
{
}
