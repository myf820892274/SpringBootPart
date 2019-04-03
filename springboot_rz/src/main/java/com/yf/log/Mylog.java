package com.yf.log;

import java.lang.annotation.*;

@Target(ElementType.METHOD)//放在方法前
@Retention(RetentionPolicy.RUNTIME)//该注解能否被反射读取
@Documented
@Inherited
public @interface Mylog {

    String value();//记录方法的功能
    String description();//详细描述方法

}
