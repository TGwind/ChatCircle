package com.generation.cricle.presenter;

import android.util.Log;

import com.generation.cricle.entity.Blog;
import com.generation.cricle.entity.PageResult;
import com.generation.cricle.essential.BaseView;
import com.generation.cricle.essential.IPresenter;
import com.generation.cricle.essential.IView;
import com.generation.cricle.retrofit.ApiServiceFactory;
import com.generation.cricle.retrofit.Result;

import java.util.List;


import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ExplorePresenter implements IPresenter.exploreReq {
    IView.ExploreView exploreView;

    public ExplorePresenter(IView.ExploreView exploreView) {
        this.exploreView = exploreView;
    }

    @Override
    public BaseView getBase() {
        return exploreView;
    }

    //观察者
    Observer<Result<PageResult<Blog>>> blogObserver = new Observer<Result<PageResult<Blog>>>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(Result<PageResult<Blog>> PageResult) {
            exploreView.showBlogs(PageResult.getData().getBlogList(),PageResult.getData().getBlogCount());
        }


        @Override
        public void onError(Throwable e) {
            Log.e("error",e.toString());
        }

        @Override
        public void onComplete() {

        }
    };

    @Override
    public void getBlogs(Integer pageNum,Integer pageSize) {
        ApiServiceFactory.getInstance().getBlogs(pageNum,pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(blogObserver);
    }

}

