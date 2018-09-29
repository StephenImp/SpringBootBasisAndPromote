package com.cn.springmvc.annotation;

import java.lang.annotation.*;

/**
 * Created by admin on 2018/8/7.
 */

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnjoyRequestParam {
    String value() default "";//注解的属性值
}
