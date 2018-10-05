package com.cn.exception;


import com.cn.eunms.ResultEnum;

/**
 * Spring框架的事物只会对RuntimeException的异常进行回滚
 * Created by admin on 2017/11/1.
 */
public class GirlException extends RuntimeException{

    private  Integer code;

    public GirlException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
