package com.cn.springmvc.annotation;

import java.lang.annotation.*;

/**
 * Created by admin on 2018/8/13.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface ComponentScan {

    String[] value() default {};

    String[] basePackages() default {};
}
