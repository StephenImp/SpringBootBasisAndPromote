package com.cn.controller;

import com.cn.dao.GirlRepository;
import com.cn.entity.Girl;
import com.cn.entity.Result;
import com.cn.service.GirlService;
import com.cn.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by admin on 2017/10/30.
 */
@RestController
public class GirlController {

    @Autowired
    private GirlRepository girlRepository;

    @Autowired
    private GirlService girlService;

    @GetMapping(value = "/girls")
    public List<Girl> girlList(){
       return girlRepository.findAll();

    }

    /**
     * 添加返回对象
     * @return
     */
    @PostMapping(value = "/girls")
    public Result<Girl> girlAdd(@RequestBody@Valid Girl girl, BindingResult bindingResult){

        Result result = new Result();

        if(bindingResult.hasErrors()){
            return  ResultUtil.error(1,bindingResult.getFieldError().getDefaultMessage());
        }

        girl.setAge(girl.getAge());

        System.out.println(girl.getAge());
        girl.setCupSize(girl.getCupSize());

        return ResultUtil.success(girlRepository.save(girl));
    }

    /**
     * 查询一个女生
     * @return
     */
    @GetMapping(value = "/girls/{id}")
    public Girl girlFindOne(@PathVariable("id") Integer id){
        return girlRepository.findOne(id);
    }

    /**
     *更新
     * @return
     */
   @PutMapping(value = "/girls/{id}")
    public Girl girlUpdate(@PathVariable("id") Integer id,
                           @RequestParam("cupSize") String cupSize,
                           @RequestParam("age") Integer age){

        Girl girl = new Girl();
        girl.setId(id);
        girl.setAge(age);
        girl.setCupSize(cupSize);

        return girlRepository.save(girl);
    }

    /**
     *删除
     * @return
     */
    @DeleteMapping(value = "/girls/{id}")
    public void girlDelete(@PathVariable("id") Integer id){

        girlRepository.delete(id);

    }
    /**
     * 通过年龄查询女生列表
     */
    @GetMapping(value = "/girls/age/{age}")
    public List<Girl> girlFindByAge(@PathVariable("age") Integer age){
        return girlRepository.findByAge(age);
    }

    /**
     * 事物管理插入数据
     */
    @PostMapping(value = "/girls/two")
    public void insertInto(){

        girlService.insertInto();
    }

    @GetMapping(value = "girls/getAge/{id}")
     public void getAge(@PathVariable("id") Integer id)throws Exception{
        girlService.getAge(id);
     }

}
