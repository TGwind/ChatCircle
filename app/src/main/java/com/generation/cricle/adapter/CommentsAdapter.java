package com.generation.cricle.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.generation.cricle.entity.Blog;
import com.generation.cricle.entity.Comment;
import com.generation.cricle.util.SelfUser;
import com.generation.cxq.R;

import butterknife.BindView;

public class CommentsAdapter extends BaseQuickAdapter<Comment, BaseViewHolder> implements LoadMoreModule {
    public CommentsAdapter() {
        super(R.layout.item_comment);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Comment comment) {
        ImageView avatar = helper.getView(R.id.imageCommenterAvatar);
        TextView textAuthor = helper.getView(R.id.textCommenterName);
        TextView textComment = helper.getView(R.id.textComment);
        TextView textCommentTime = helper.getView(R.id.textCommentTime);

        //设置头像
        Glide.with(getContext())  // 传入上下文
                .load(comment.getAvatar())  // 传入图片的 URL 或本地文件路径
                .into(avatar); // 传入要设置图片的 CircleImageView 对象
        textAuthor.setText(comment.getName());
        textComment.setText(comment.getContent());
        textCommentTime.setText(comment.getCreatedAt());

    }
}