package com.generation.cricle.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.generation.cricle.entity.Blog;
import com.generation.cxq.R;

import butterknife.BindView;

public class BlogAdapter extends BaseQuickAdapter<Blog, BaseViewHolder> implements LoadMoreModule {
    public BlogAdapter() {
        super(R.layout.item_blog);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Blog blog) {
        TextView textTitle = helper.getView(R.id.textTitle);
        ImageView imageAvatar = helper.getView(R.id.imageAvatar);
        //设置头像
        Glide.with(getContext())  // 传入上下文
                .load(blog.getAvatar())  // 传入图片的 URL 或本地文件路径
                .into(imageAvatar); // 传入要设置图片的 CircleImageView 对象
        textTitle.setText(blog.getTitle());

//        TextView textContent = helper.getView(R.id.textContent);
//        textContent.setText(blog.getContent());

        TextView textAuthor = helper.getView(R.id.textAuthor);
        textAuthor.setText(blog.getName());

        TextView textTime = helper.getView(R.id.textTime);
        textTime.setText(blog.getCreatedAt());

    }
}