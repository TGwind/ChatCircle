package com.generation.cricle.util;

import com.generation.cricle.entity.User;

public class SelfUser {
    private static SelfUser instance;
    private User currentUser;

    private SelfUser() {
        // 私有构造函数
    }

    public static SelfUser getInstance() {
        if (instance == null) {
            instance = new SelfUser();
        }
        return instance;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
}