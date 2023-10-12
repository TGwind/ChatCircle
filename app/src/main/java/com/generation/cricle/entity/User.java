package com.generation.cricle.entity;

import com.generation.cricle.essential.IModel;
import com.generation.cricle.util.DBOpenHelper;
import com.generation.cricle.util.LogInfo;
import com.generation.cricle.util.SelfUser;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class User implements IModel.LoginModel {
    private Long id;
    private String name;
    private String password;
    private String avatar;
    private int sex;
    private String phone;


    public User() {
    }

    public User(Long id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean validatePassword(String userName, String userPassword) {
        User that = this;
        // 创建线程并执行任务
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Connection conn = null;
                conn = DBOpenHelper.getConn();
                String sql = "select * from user where name='" + userName + "'";
                Statement st;
                try {
                    st = conn.createStatement();
                    ResultSet rs = st.executeQuery(sql);
                    while (rs.next()) {
                        //因为查出来的数据试剂盒的形式，所以我们新建一个javabean存储
                        LogInfo.e("Mysql返回数据：" + rs.getString("name") + rs.getString("avatar")+rs.getString("userId"));
                        setName(rs.getString("name"));
                        setId(Long.valueOf(rs.getString("userId")));
                        setPassword(rs.getString("password"));
                    }
                    st.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        });
        // 启动线程
        thread.start();
        // 主线程等待子线程执行完毕
        try {
            thread.join();      //等待指定的线程执行完毕
            if (this.password.equals(userPassword)) {
                SelfUser.getInstance().setCurrentUser(that);
                return true;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;

    }

}
