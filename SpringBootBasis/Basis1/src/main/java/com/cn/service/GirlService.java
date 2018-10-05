package com.cn.service;

import com.cn.dao.GirlRepository;
import com.cn.entity.Girl;
import com.cn.eunms.ResultEnum;
import com.cn.exception.GirlException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by admin on 2017/10/30.
 */
@Service
public class GirlService {

    @Autowired
    private GirlRepository girlRepository;

    @Transactional(rollbackFor = Exception.class )
    public  void insertInto(){

        Girl girl = new Girl();
        girl.setCupSize("A");
        girl.setAge(10);
        girlRepository.save(girl);

        Girl girl1 = new Girl();
        girl1.setAge(30);
        girl1.setCupSize("FFFFF");
        girlRepository.save(girl1);

    }

    public void getAge(Integer id) throws Exception {
        Girl girl = girlRepository.findOne(id);
        Integer age = girl.getAge();

        if (age < 10) {

            throw new GirlException(ResultEnum.PRIMARY_SCHOOL);
        } else if (age > 10 && age < 16) {

            throw new GirlException(ResultEnum.MIDDLE_SCHOOL);
        }

    }

    /**
     * 通过一个Id查询女生的信息
     * @return
     */
    public Girl findOne(Integer id){
        return girlRepository.findOne(id);

    }
}
