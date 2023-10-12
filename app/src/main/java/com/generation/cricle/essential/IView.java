package com.generation.cricle.essential;

import android.widget.BaseExpandableListAdapter;

import com.generation.cricle.entity.Blog;
import com.generation.cricle.entity.Comment;

import java.util.List;

//所有IView的接口
public interface IView {
    interface LoginView extends BaseView{
        void successLogin();
        void failLogin();

    }

    interface ExploreView extends BaseView{
        void showBlogs(List<Blog> blogList,long blogCount);
    }
    interface UserView extends BaseView{
        void showSentence(String s);
    }
    interface PublishView extends BaseView{
        void successAdd();
    }
    interface BlogDetailView extends BaseView{
        void addComment();
        void showComments(List<Comment> comments);
    }
}
