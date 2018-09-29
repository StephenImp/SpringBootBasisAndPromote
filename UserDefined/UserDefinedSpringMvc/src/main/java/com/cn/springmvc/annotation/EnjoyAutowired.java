package com.cn.springmvc.annotation;

import java.lang.annotation.*;

/**
 * Created by admin on 2018/8/7.
 */
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
//@Inherited    表示此注解可以被继承了
public @interface EnjoyAutowired {

    String value() default "";//注解的属性值
}
