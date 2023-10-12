package com.generation.cricle.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.generation.cricle.adapter.CommentsAdapter;
import com.generation.cricle.base.BaseActivity;
import com.generation.cricle.entity.Blog;
import com.generation.cricle.entity.Comment;
import com.generation.cricle.essential.BasePresenter;
import com.generation.cricle.essential.IView;
import com.generation.cricle.presenter.BlogDetailPresenter;
import com.generation.cxq.R;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;

public class BlogDetailActivity extends BaseActivity implements IView.BlogDetailView {
    @BindView(R.id.blog_detail_author)
    TextView textAuthor;
    @BindView(R.id.blog_detail_content)
    TextView textContent;
    @BindView(R.id.blog_detail_time)
    TextView textTime;
    @BindView(R.id.recyclerViewComments)
    RecyclerView recyclerViewComments;
    Blog blogData;
    CommentsAdapter commentsAdapter = new CommentsAdapter();
    BlogDetailPresenter blogDetailPresenter;

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_blog_detail);
        Intent intent = getIntent();
        blogData = (Blog) intent.getSerializableExtra("blogData");
        Log.e("发帖数据", blogData.getTitle());
        setTitle(blogData.getTitle()); // 设置标题

        Window window = getWindow();
        window.setStatusBarColor(Color.BLUE); // 设置状态栏颜色

        ActionBar actionBar = getSupportActionBar();    //设置标题栏颜色
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.BLUE));
        }


    }

    @Override
    protected void initView() {

        blogDetailPresenter = (BlogDetailPresenter) getModel();

        textContent.setText(blogData.getContent());
        textAuthor.setText(blogData.getName());
        textTime.setText(blogData.getCreatedAt().toString());
        recyclerViewComments.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewComments.setAdapter(commentsAdapter);
        blogDetailPresenter.getComments(blogData.getId());

    }

    @Override
    protected BasePresenter initModel() {
        return new BlogDetailPresenter(this);
    }

    @Override
    public void addComment() {

    }

    @Override
    public void showComments(List<Comment> comments) {
        Log.e("评论信息",comments.toString());
        commentsAdapter.setList(comments);

    }
}
