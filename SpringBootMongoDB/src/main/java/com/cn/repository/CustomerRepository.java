package com.cn.repository;

import com.cn.entity.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by admin on 2018/8/20.
 * <p>
 * MongoRepository <T,PK>同样也是采用了两个泛型参数，
 * T：实体类类型。
 * PK：T实体类内的主键类型，如：String。
 * <p>
 * collectionResourceRel：该参数配置映射MongoDB内的Collection名称。
 * path：该参数配置映射完成rest后访问的路径前缀。
 */
@RepositoryRestResource(collectionResourceRel = "customer", path = "customer")
public interface CustomerRepository extends MongoRepository<Customer, String> {

    /**
     * 更加名字查询数据
     *
     * @param firstName 名字
     * @return
     */
    List<Customer> findByFirstName(@Param("firstName") String firstName);

    /**
     * 根据姓氏查询出最靠前的一条数据
     *
     * @param lastName 姓氏
     * @return
     */
    Customer findTopByLastName(@Param("lastName") String lastName);

}
