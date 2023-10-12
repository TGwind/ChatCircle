package com.generation.cricle.activity;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.generation.cricle.adapter.ChatAdapter;
import com.generation.cricle.base.BaseActivity;
import com.generation.cricle.database.MessageHelper;
import com.generation.cricle.entity.Message;
import com.generation.cricle.essential.BasePresenter;
import com.generation.cxq.R;

import org.json.JSONException;
import org.json.JSONObject;


import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class ChatActivity extends BaseActivity {
    String text = "";  //临时存储对话消息
    WebSocket webSocket;
    @BindView(R.id.recyclerViewChat)
    RecyclerView recyclerViewChat;
    @BindView(R.id.buttonSend)
    Button buttonSend;

    @BindView(R.id.editTextMessage)
    TextView editTextMessage;

    ChatAdapter chatAdapter;

    Context context = this;

    String conversationId; //会话id

    @Override
    protected void initView() {
        chatAdapter = new ChatAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewChat.setLayoutManager(layoutManager);

        Intent intent = getIntent();
        conversationId = intent.getStringExtra("conversation_id");
        recyclerViewChat.setAdapter(chatAdapter);

        //获取数据库消息列表
        List<Message> messageList = MessageHelper.getMessagesByConversationId(this, conversationId);
        chatAdapter.addData(messageList);

        webSocketConnect(this ,chatAdapter); //websocket连接服务器

        //设置按钮监听器
        buttonSend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // 在发送按钮的点击事件中调用
                String usrTxt = editTextMessage.getText().toString();
                Message userMessage = new Message(conversationId, usrTxt, "user");
                chatAdapter.addData(userMessage);
                Log.d(TAG, "用户prompt-->" + userMessage.getContent());
                MessageHelper.addMessage(view.getContext(),userMessage);
                Message GPTMessage = new Message(conversationId, "......", "GPT");
                chatAdapter.addData(GPTMessage);
                //更新页面显示

                webSocket.send(usrTxt);
                text = "";
                editTextMessage.setText("");
            }

        });
    }

    @Override
    protected BasePresenter initModel() {
        return null;
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_chat);
    }


    private void webSocketConnect(Context context , ChatAdapter chatAdapter) {//定义websocket连接函数
        UUID uuid = UUID.randomUUID();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("ws://10.90.77.142:33117/websocket/" + uuid).build();
        webSocket = client.newWebSocket(request, new WebSocketListener() {
            public void onOpen(@NonNull WebSocket webSocket, @NonNull okhttp3.Response response) {
                // 连接成功后的初始化操作
                Log.d(TAG, "WebSocket连接成功");
            }

            //接收消息监听器
            public void onMessage(@NonNull WebSocket webSocket, @NonNull String resp) {
//                Log.d(TAG, resp);
                // 判断是否为 [DONE]
                if (resp.equals("[DONE]")) {
                    Log.d(TAG, text);
                    Log.d(TAG, "===接收完毕===");
                    MessageHelper.addMessage(context ,chatAdapter.getItem(chatAdapter.getData().size()-1));
                } else {
                    try {
                        // 将收到的消息转换成 JSONObject 对象
                        JSONObject json_data = new JSONObject(resp);
                        // 判断是否存在 content 字段
                        if (json_data.has("content")) {
                            String content = json_data.getString("content");
                            if (!content.equals("null")) {
                                // 将 content 字段的值添加到之前的文本中
                                text += content;
                                runOnUiThread(() -> {
                                    // 将消息显示在 UI 线程上
                                    //UI 组件只能在主线程（也称为 UI 线程）中更新。否则抛出这异常
                                    updateLastMessage(text);
                                });
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }


            public void onClosed(WebSocket webSocket, int code, String reason) {
                // 连接关闭时的处理逻辑
                Log.d(TAG, "WebSocket连接关闭，code：" + code + "，reason：" + reason);
            }

            public void onFailure(WebSocket webSocket, Throwable t, okhttp3.Response response) {
                // 连接失败时的处理逻辑
                Log.e(TAG, "WebSocket连接失败", t);
            }
        });
    }

    //动态更新最后一条消息，实现打字机效果
    public void updateLastMessage(String text) {
        int lastPosition = chatAdapter.getData().size()-1;
        if (lastPosition > 0) { // 如果有最后一条消息了
            chatAdapter.getItem(lastPosition).setContent(text);
            // 更新最后一条消息的内容
            chatAdapter.notifyItemChanged(lastPosition); // 通知 RecyclerView 更新最后一条消息的内容
        }
    }

    @Override
    protected void onDestroy() {  //页面隐藏时断开webSocket连接
        super.onDestroy();
        if (webSocket != null) {
            webSocket.close(1000, "Activity onDestroy");
            webSocket = null;
        }

    }

}
