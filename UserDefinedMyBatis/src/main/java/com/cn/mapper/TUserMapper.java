package com.cn.mapper;

import com.cn.entity.TUser;

import java.util.List;

/**
 * Created by Administrator on 2018\9\12 0012.
 */
public interface TUserMapper {

    //调用sqlSession中的selectOne()
    TUser selectByPrimaryKey(Integer id);

    //调用sqlSession中的selectList()
    List<TUser> selectAll();
}
