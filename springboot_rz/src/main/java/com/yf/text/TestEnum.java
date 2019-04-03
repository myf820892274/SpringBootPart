package com.yf.text;

public class TestEnum {
    public static void main(String[] args){
        MyFirstEnum myFirstEnum = MyFirstEnum.JAVA;
         switch (myFirstEnum){
             case H5:
                 System.out.println("您学的是H5");
                 break;
             case PYTHON:
                 System.out.println("您学的是python");
                 break;
             case JAVA:
                 System.out.println("您学的是java");
                 break;
         }

        System.out.println(MySecondEnum.NOMAL.getValue());

    }
}
