package com.generation.cricle.presenter;

import android.util.Log;

import com.generation.cricle.entity.User;
import com.generation.cricle.essential.BaseView;
import com.generation.cricle.essential.IPresenter;
import com.generation.cricle.essential.IView;
import com.generation.cricle.retrofit.ApiServiceFactory;
import com.generation.cricle.retrofit.Result;
import com.generation.cricle.util.SelfUser;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;



public class LoginPresenter  implements IPresenter.login {
    private IView.LoginView loginView;

    public LoginPresenter(IView.LoginView loginView ) {
        this.loginView = loginView;
    }

    Observer<Result<User>> loginObserver = new Observer<Result<User>>() {
        @Override
        public void onSubscribe(Disposable d) {

        }
        @Override
        public void onNext(Result<User> result) {
            if(result.getData()!=null){
                SelfUser.getInstance().setCurrentUser(result.getData());
                Log.e("登录成功",result.getData().getId().toString()+result.getData().getName().toString());
                loginView.successLogin();
            }else {
                loginView.failLogin();
            }
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    };
    @Override
    public void checkLogin(String body) {
        //创建一个线程处理网络请求，防止主线程阻塞
        new Thread(new Runnable() {
            @Override
            public void run() {
                ApiServiceFactory.getInstance().login(body)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(loginObserver);
            }
        }).start();
    }

    @Override
    public BaseView getBase() { //获取视图
        return loginView;
    }
}
