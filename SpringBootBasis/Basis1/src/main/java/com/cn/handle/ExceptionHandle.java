package com.cn.handle;

import com.cn.entity.Result;
import com.cn.exception.GirlException;
import com.cn.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 捕获异常
 * 控制层的异常抛到这里
 * Created by admin on 2017/11/1.
 */
@ControllerAdvice
public class ExceptionHandle {

    private final static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handle(Exception e){

        if(e instanceof GirlException){

            GirlException girlException = (GirlException)e;
            return  ResultUtil.error(girlException.getCode(),girlException.getMessage());
        }

        logger.info("系统异常");
        return ResultUtil.error(-1,"未知错误");
    }
}
