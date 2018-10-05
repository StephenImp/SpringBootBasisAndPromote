package com.cn.utils;


import com.cn.entity.Result;

/**
 * Created by admin on 2017/11/1.
 */
public class ResultUtil {

    public static Result success(Object object){
        Result result = new Result();
        result.setCode(0);
        result.setMsg("成功");
        result.setDate(object);

        return result;
    }

    public static Result success(){
        return success(null);
    }


    public static Result error(Integer code,String msg){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);

        return result;
    }

}
