package com.yf.anno;

import java.sql.Connection;

@DBAnno(value = "oracle")
public class TestAnno2 {
    //mysql:com.mysql.jdbc.Driver
    //oracle:oracle.jdbc.OracleDriver
    public Connection getConnection(){
        try{
            Class c = TestAnno2.class;
            DBAnno dbAnno = (DBAnno) c.getAnnotation(DBAnno.class);
            String DriverName = null;
            if (dbAnno.value().equals("mysql")){
                DriverName = "com.mysql.jdbc.Driver";
                System.out.println("mysql");
            }
            if (dbAnno.value().equals("oracle")){
                DriverName = "oracle.jdbc.OracleDriver";
                System.out.println("oracle");
            }
            //Class.forName(DriverName);
            //return DriverManager.getConnection("","","");
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static void main(String[] args){
        TestAnno2 anno2 = new TestAnno2();
        anno2.getConnection();
    }

}

