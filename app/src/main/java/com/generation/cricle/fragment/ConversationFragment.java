package com.generation.cricle.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnItemLongClickListener;
import com.generation.cricle.activity.ChatActivity;
import com.generation.cricle.adapter.ConversationAdapter;
import com.generation.cricle.base.BaseFragment;
import com.generation.cricle.database.ConversationHelper;
import com.generation.cricle.entity.Conversation;
import com.generation.cricle.entity.Message;
import com.generation.cricle.essential.BasePresenter;
import com.generation.cxq.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import butterknife.BindView;


public class ConversationFragment extends BaseFragment {
    @BindView(R.id.fab_add_conversation)
    FloatingActionButton floatingActionButton;
    @BindView(R.id.conversation_list)
    RecyclerView conversation_list;

    ConversationAdapter conversationAdapter = new ConversationAdapter();

    private List<Conversation> getConversations() {
        // 从本地数据库或服务器获取会话数据
        List<Conversation> conversations = ConversationHelper.getAllConversations(getContext());
        return conversations;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_conversation;
    }

    @Override
    public void initView(View view) {
        conversationAdapter = new ConversationAdapter();
        conversation_list.setLayoutManager(new LinearLayoutManager(this.getContext()));
        conversation_list.setAdapter(conversationAdapter);
        conversationAdapter.setList(getConversations());
        //添加加载动画
        conversationAdapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.ScaleIn);        conversationAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                // 跳转到聊天界面，并传递 conversation_id 参数
                Intent intent = new Intent(getContext(), ChatActivity.class);
                intent.putExtra("conversation_id", conversationAdapter.getItem(position).getConversationId());
                startActivity(intent);
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 添加新的会话记录
                Conversation conversation = conversationAdapter.addConversation("新会话");
                ConversationHelper.addConversation(getContext(), conversation);

//                 跳转到聊天界面，并传递 conversation_id 参数
                Intent intent = new Intent(getContext(), ChatActivity.class);
                intent.putExtra("conversation_id", conversation.getConversationId());
                startActivity(intent);
            }
        });
        conversationAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                // 弹出操作菜单
                PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
                popupMenu.getMenuInflater().inflate(R.menu.conversation_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @SuppressLint("NonConstantResourceId")
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_delete_conversation:
                                // 删除会话
                                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                                builder.setTitle("提示");
                                builder.setMessage("确定要删除该会话吗？");
                                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // 从数据源中删除该会话
//                                        int position = getAdapterPosition();
                                        ConversationHelper.deleteConversationById(view.getContext(), conversationAdapter.getItem(position).getConversationId());
                                        conversationAdapter.removeAt(position);

                                        // 刷新界面
                                        conversationAdapter.notifyItemChanged(position);
                                    }
                                });
                                builder.setNegativeButton("取消", null);
                                builder.show();
                                break;
                            case R.id.menu_alter_title:
                                // 修改标题
                                showEditTitleDialog(view.getContext(), position);
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
                return true;
            }
        });
    }

    private void showEditTitleDialog(Context context, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("修改标题");

        // 创建一个 EditText 用于用户输入新的标题
        final EditText editText = new EditText(context);
        builder.setView(editText);

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newTitle = editText.getText().toString();
                // 处理用户输入的新标题
                updateTitle(context, newTitle, position);
            }
        });

        builder.setNegativeButton("取消", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void updateTitle(Context context, String newTitle, int position) {
        ConversationHelper.updateConversationLabel(context, conversationAdapter.getItem(position).getConversationId(), newTitle);
        // 更新数据源和界面以反映用户输入的新标题
        // 修改元素的属性
        conversationAdapter.getItem(position).setLabel(newTitle);
        // 调用以下方法来刷新当前位置的 item
        conversationAdapter.notifyItemChanged(position);
    }


    @Override
    protected BasePresenter initModel() {
        return null;
    }

}
