package com.generation.cricle.presenter;

import android.util.Log;

import com.generation.cricle.essential.BasePresenter;
import com.generation.cricle.essential.BaseView;
import com.generation.cricle.essential.IPresenter;
import com.generation.cricle.essential.IView;
import com.generation.cricle.retrofit.ApiServiceFactory;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserPresenter implements IPresenter.user {
    IView.UserView userView;

    @Override
    public BaseView getBase() {
        return userView;
    }

    public UserPresenter(IView.UserView userView) {
        this.userView = userView;
    }

    Observer<String> dayObserver = new Observer<String>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(String s) {
            Log.e("每日一言", s);
            // 更新视图
            userView.showSentence(s);
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    };

    @Override
    public void getDaySentence() {
        Call<String> call = ApiServiceFactory.getInstance().getSentence("https://api.shserve.cn/api/yiyan");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String sentence = response.body();
                    Log.d("TAG", "每日一句: " + sentence);
                    userView.showSentence(sentence);
                } else {
                    Log.d("TAG", "Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("TAG", "Error: " + t.getMessage());
            }
        });
    }
}
