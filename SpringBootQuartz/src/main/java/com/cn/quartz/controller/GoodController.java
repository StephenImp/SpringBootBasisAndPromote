package com.cn.quartz.controller;

import com.cn.quartz.entity.GoodInfoEntity;
import com.cn.quartz.service.GoodInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/good")
public class GoodController
{
    /**
     * 商品业务逻辑实现
     */
    @Autowired
    private GoodInfoService goodInfoService;
    /**
     * 添加商品
     * @return
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @ResponseBody
    public Long save(@RequestBody GoodInfoEntity good) throws Exception
    {
        return goodInfoService.saveGood(good);
    }
}
