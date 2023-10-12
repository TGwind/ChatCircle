package com.generation.cricle.essential;

public interface IModel {
    interface LoginModel {
        //默认情况下它们是公开的（public）且抽象的（abstract）,这两个修饰符可省略
        boolean validatePassword(String userName, String userPassword);

    }
}
