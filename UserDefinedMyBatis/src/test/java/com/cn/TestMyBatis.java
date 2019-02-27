package com.cn;

import com.cn.entity.TUser;
import com.cn.mapper.TUserMapper;
import com.cn.session.SqlSession;
import com.cn.session.SqlSessionFactory;
import org.junit.Test;

import java.io.IOException;
import java.util.List;


/**
 * Created by Administrator on 2018\9\12 0012.
 */
public class TestMyBatis {

    //1.实例化sqlSessionFactory,加载数据库配置文件信息以及mapper.xml文件到configuration对象
    SqlSessionFactory factory = new SqlSessionFactory();

    @Test
    public void testMyBatis(){

        //2.获取sqlsession对象
        SqlSession session = factory.opeanSession();

        //3.通过代理模式跨越面向接口编程和ibatis编程模型的鸿沟
        TUserMapper userMapper = session.getMapper(TUserMapper.class);

        //4.遵循jdbc规范，通过底层的4大对象的合作完成数据查询和数据转化
        /**
         * 这里执行动态代理类的Invoke()
         * 根据返回类型就能判断出调用的是哪一个方法。
         */
        TUser user = userMapper.selectByPrimaryKey(1);

        System.out.println(user);

        System.out.println("-------------------------------");

        List<TUser> selectAll = userMapper.selectAll();

        for (TUser tUser : selectAll) {
            System.out.println(tUser);
        }

    }

    /**
     * 本质分析，基于ibatis编程模型
     * @throws IOException
     */
    @Test
    public void originalOperation()throws IOException{

        //2.获取sqlSession
        SqlSession sqlSession = factory.opeanSession();

        //3.执行查询语句并返回结果
        TUser user = sqlSession.selectOne("",1);
        System.out.println(user.toString());

        System.out.println("----------------------------------------------");

        List<TUser> users = sqlSession.selectList("",0);

        for (TUser tUser:users) {
            System.out.println(tUser);
        }
    }
}
