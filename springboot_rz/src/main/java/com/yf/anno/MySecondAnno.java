package com.yf.anno;

import java.lang.annotation.*;

@Target(ElementType.METHOD)//方法前，用来描述方法
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface MySecondAnno {

    String name() default "Anno";
    String value() default "我的第二个自定义注解";
}
