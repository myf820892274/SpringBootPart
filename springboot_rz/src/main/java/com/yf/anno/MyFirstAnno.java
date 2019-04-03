package com.yf.anno;

import java.lang.annotation.*;

/**
 * 元注解
 * @Target 注解的使用位置
 * @Retention
 *@Documented
 *@Inherited
 */

@Target(ElementType.TYPE)//类前
@Retention(RetentionPolicy.RUNTIME)//注解能被反射读取到\
@Documented
@Inherited
public @interface MyFirstAnno {

    String name() default "Anno";
    String value() default "我的第一个自定义注解";

}
