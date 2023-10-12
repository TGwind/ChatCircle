package com.generation.cricle.presenter;

import android.util.Log;

import com.generation.cricle.entity.User;
import com.generation.cricle.essential.BasePresenter;
import com.generation.cricle.essential.BaseView;
import com.generation.cricle.essential.IView;
import com.generation.cricle.retrofit.ApiServiceFactory;
import com.generation.cricle.retrofit.Result;

import java.util.Objects;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PublishPresenter implements BasePresenter {
    IView.PublishView publishView;

    public PublishPresenter(IView.PublishView publishView){
        this.publishView = publishView;
    }
    Observer<Result<String>> publishObserver = new Observer<Result<String>>(){

        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(Result<String> result) {
            if(Objects.equals(result.getCode(), "200")){
                Log.e("添加blog请求",result.getData());
                publishView.successAdd();
            }else {
                Log.e("添加blog请求",result.getData());
            }
        }

        @Override
        public void onError(Throwable e) {
            Log.e("添加blog请求",e.toString());

        }

        @Override
        public void onComplete() {

        }
    };
    //添加blog
    public void addBlog(String body){
        ApiServiceFactory.getInstance().addBlog(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(publishObserver);
    };
    @Override
    public BaseView getBase() {
        return publishView;
    }
}
