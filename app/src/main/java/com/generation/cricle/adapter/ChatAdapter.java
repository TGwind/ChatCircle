package com.generation.cricle.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.generation.cricle.entity.Message;
import com.generation.cricle.util.Constants;
import com.generation.cricle.util.SelfUser;
import com.generation.cxq.R;

public class ChatAdapter extends BaseQuickAdapter<Message, BaseViewHolder> {

    public ChatAdapter() {
        super(R.layout.item_chat_message);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Message message) {
        ImageView imageViewAvatar = helper.getView(R.id.imageViewAvatar);
        TextView text_message_body = helper.getView(R.id.text_message_body);

        if (message.getRole().equals("user")) {
            Glide.with(getContext())
                    .load(SelfUser.getInstance().getCurrentUser().getAvatar())
                    .into(imageViewAvatar);
        } else {
            Glide.with(getContext())
                    .load(Constants.GPT_AVATAR)
                    .into(imageViewAvatar);
        }
        text_message_body.setText(message.getContent());

    }


}
