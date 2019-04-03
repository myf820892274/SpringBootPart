package com.yf.text;

import org.apache.shiro.crypto.hash.Md5Hash;

public class Testpwd {

    public static void main(String[] args){
        Md5Hash md5Hash = new Md5Hash("12345","admin",1024);
        System.out.println(md5Hash.toString());
        md5Hash = new Md5Hash("111","jary",1024);
        System.out.println(md5Hash.toString());

    }

}
