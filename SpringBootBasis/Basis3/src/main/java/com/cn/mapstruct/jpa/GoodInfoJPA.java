package com.cn.mapstruct.jpa;

import com.cn.mapstruct.bean.GoodInfoBean;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GoodInfoJPA
    extends JpaRepository<GoodInfoBean,Long>
{
    
}
