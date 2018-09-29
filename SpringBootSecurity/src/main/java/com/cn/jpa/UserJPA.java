package com.cn.jpa;

import com.cn.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface UserJPA extends JpaRepository<UserEntity,Long>
{
    //查询大于20岁的用户
    @Query(value = "select * from t_user where t_age > ?1",nativeQuery = true)
    public List<UserEntity> nativeQuery(int age);

    //根据用户名、密码删除一条数据

    /**
     在 @Query 注解中编写 JPQL 语句， 但必须使用 @Modifying 进行修饰.
     以通知SpringData， 这是一个 UPDATE 或 DELETE 操作
     */
    @Modifying
    @Query(value = "delete from t_user where t_name = ?1 and t_pwd = ?2",nativeQuery = true)
    public void deleteQuery(String name, String pwd);
}
