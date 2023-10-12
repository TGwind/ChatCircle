package com.generation.cricle.essential;

import com.generation.cricle.entity.User;

//所有Presenter的接口
public interface IPresenter {
    //登录presenter接口
    interface login extends BasePresenter {
        void checkLogin(String body);
    }

    interface exploreReq extends BasePresenter {
        void getBlogs(Integer pageNum,Integer pageSize);
    }
    interface user extends BasePresenter{
        void getDaySentence();
    }
}
