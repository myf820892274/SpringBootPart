package com.yf.anno;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Method;

@MyFirstAnno
public class TestAnno {

    @MySecondAnno(value = "展示用户信息",name = "seAnno")
    public void show(){
        System.out.println("hello 注解！！！");
    }


    public static void main(String[] args) throws Exception{

        TestAnno testAnno = new TestAnno();
        Class c = testAnno.getClass();
//        AnnotatedType annotatedType = c.getAnnotatedSuperclass();
//        System.out.println(annotatedType instanceof MyFirstAnno);

        MyFirstAnno anno = (MyFirstAnno) c.getAnnotation(MyFirstAnno.class);
        System.out.println(anno);
        System.out.println(anno.name()+"---"+anno.value());

        //反射让注解具有灵魂
        Method method = c.getDeclaredMethod("show");
        //得到方法的注解
        MySecondAnno mySecondAnno = method.getDeclaredAnnotation(MySecondAnno.class);
        System.out.println(mySecondAnno.value()+"--"+mySecondAnno.name());
        if (mySecondAnno!=null){
            //new
        }

    }
}
