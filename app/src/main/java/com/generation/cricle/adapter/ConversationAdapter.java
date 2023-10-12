package com.generation.cricle.adapter;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.generation.cricle.entity.Blog;
import com.generation.cricle.entity.Conversation;
import com.generation.cxq.R;

import java.util.Date;

public class ConversationAdapter extends BaseQuickAdapter<Conversation, BaseViewHolder>  {
    public ConversationAdapter() {
        super(R.layout.item_conversation);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Conversation conversation) {
        ImageView conversation_avatar = helper.getView(R.id.conversation_avatar);
        TextView label = helper.getView(R.id.label);
        TextView time_text_view = helper.getView(R.id.time_text_view);
        TextView last_message_text_view = helper.getView(R.id.last_message_text_view);

        Glide.with(getContext())
                .load(conversation.getAvatar())
                .into(conversation_avatar);

        label.setText(conversation.getLabel());
        time_text_view.setText(conversation.getLastMessageTime());
        last_message_text_view.setText(conversation.getLastMessage());

    }
    public Conversation addConversation(String label) {
        // 创建新的会话实例
        Conversation conversation = new Conversation(label);
        conversation.setAvatar("https://7463-tcb-cxq-7gw1h3uze1299e21-1316471607.tcb.qcloud.la/%E9%A2%84%E8%AE%BE%E5%9B%BE%E7%89%87/%E6%99%BA%E6%85%A7%E5%B0%8F%E5%9C%88%20(2).png?sign=08878ba078432acb67ccd33a12e0ab80&t=1689573619");
        conversation.setLastMessage("欢迎加入新的会话！");
        conversation.setLastMessageTime(new Date());
        // 添加到数据源中
        this.addData(conversation);
        // 打印日志
        Log.d(TAG, "创建会话" + label);
        // 刷新指定item的位置的界面
        this.notifyItemChanged(this.getData().size());
        return conversation;
    }
}
