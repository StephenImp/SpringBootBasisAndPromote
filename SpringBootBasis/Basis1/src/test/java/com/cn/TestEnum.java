package com.cn;
import com.cn.eunms.ResultEnum;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class TestEnum {

	@Test
	public void test1(){
		Integer enum1 = ResultEnum.PRIMARY_SCHOOL.getCode();
		System.out.println(enum1);
	}
	
}
