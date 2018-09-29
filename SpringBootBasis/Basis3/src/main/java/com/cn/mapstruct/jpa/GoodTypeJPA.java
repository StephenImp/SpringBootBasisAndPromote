package com.cn.mapstruct.jpa;

import com.cn.mapstruct.bean.GoodTypeBean;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodTypeJPA
    extends JpaRepository<GoodTypeBean,Long>
{
}
