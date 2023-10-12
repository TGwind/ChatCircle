package com.generation.cricle.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//jdbc连接数据库
public class DBOpenHelper {
   private static String diver = "com.mysql.jdbc.Driver";    //com.mysql.cj.jdbc.Driver是8.0版本的mysql
   //加入utf-8是为了后面往表中输入中文，表中不会出现乱码的情况
   private static String url = "jdbc:mysql://10.152.177.187:3306/cxq?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
   private static String user = "root";//用户名
   private static String password = "123456";//密码
    /*
    * 连接数据库
    * */
   public static Connection getConn(){
       Connection conn = null;
       try {
           Class.forName(diver);
           conn = DriverManager.getConnection(url,user,password);//获取连接
       } catch (ClassNotFoundException | SQLException e) {
           e.printStackTrace();
       }
       return conn;
   }
}
