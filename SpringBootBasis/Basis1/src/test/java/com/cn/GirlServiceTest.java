package com.cn;

import com.cn.entity.Girl;
import com.cn.service.GirlService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by admin on 2017/11/1.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GirlServiceTest {

    @Autowired
    private GirlService girlService;

    @Test
    public  void findOneTest(){
        Girl girl = girlService.findOne(13);
        Assert.assertEquals(new Integer(9),girl.getAge());
    }
}
