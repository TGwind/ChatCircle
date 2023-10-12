package com.generation.cricle.presenter;

import com.generation.cricle.entity.Comment;
import com.generation.cricle.essential.BasePresenter;
import com.generation.cricle.essential.BaseView;
import com.generation.cricle.essential.IView;
import com.generation.cricle.retrofit.ApiServiceFactory;
import com.generation.cricle.retrofit.Result;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BlogDetailPresenter implements BasePresenter {
    IView.BlogDetailView blogDetailView;

    public BlogDetailPresenter(IView.BlogDetailView blogDetailView) {
        this.blogDetailView = blogDetailView;
    }

    @Override
    public BaseView getBase() {
        return blogDetailView;
    }

    Observer<Result<List<Comment>>> commentsObserver = new Observer<Result<List<Comment>>>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(Result<List<Comment>> result) {
            blogDetailView.showComments(result.getData());
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    };

    public void getComments(Long blogId) {
        ApiServiceFactory.getInstance().getBlogComments(blogId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(commentsObserver);
    }
}
