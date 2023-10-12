package com.generation.cricle.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.generation.cricle.entity.Message;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class MessageHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "messages.db"; // 数据库名称
    private static final int DATABASE_VERSION = 1; // 数据库版本号

    public MessageHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建 messages 表
        db.execSQL("CREATE TABLE messages ("
                + "id TEXT, "
                + "conversationId TEXT, "
                + "role TEXT, "
                + "content TEXT, "
                + "time INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 如果需要升级数据库，可以在这里实现
    }

    // 添加消息记录列表
    public static void addMessageList(Context context, List<Message> messages) {
        // 创建数据库 helper
        MessageHelper dbHelper = new MessageHelper(context);
        // 获取可写的数据库对象
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // 开始事务
        db.beginTransaction();

        try {
            // 遍历消息列表
            for (Message message : messages) {
                // 创建 ContentValues 对象，用于存储新的消息记录
                ContentValues values = new ContentValues();
                values.put("id", UUID.randomUUID().toString());
                values.put("conversationId", message.getConversationId());
                values.put("role", message.getRole());
                values.put("content", message.getContent());
                values.put("time", new Date().getTime());
                // 插入新的消息记录
                long id = db.insert("messages", null, values);

                if (id != -1) {
                    Log.d("MessageHelper", "插入成功" + id);
                } else {
                    Log.d("MessageHelper", "插入失败");
                }
            }

            // 标记事务成功
            db.setTransactionSuccessful();
        } finally {
            // 结束事务
            db.endTransaction();
            // 关闭数据库连接
            db.close();
        }
    }

    // 添加消息记录列表
    public static void addMessage(Context context, Message message) {
        // 创建数据库 helper
        MessageHelper dbHelper = new MessageHelper(context);
        // 获取可写的数据库对象
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // 开始事务
        db.beginTransaction();

        try {

            // 创建 ContentValues 对象，用于存储新的消息记录
            ContentValues values = new ContentValues();
            values.put("id", UUID.randomUUID().toString());
            values.put("conversationId", message.getConversationId());
            values.put("role", message.getRole());
            values.put("content", message.getContent());
            values.put("time", new Date().getTime());
            // 插入新的消息记录
            long id = db.insert("messages", null, values);

            if (id != -1) {
                Log.d("MessageHelper", "插入成功" + id);
            } else {
                Log.d("MessageHelper", "插入失败");
            }
            // 标记事务成功
            db.setTransactionSuccessful();
        } finally {
            // 结束事务
            db.endTransaction();
            // 关闭数据库连接
            db.close();
        }
    }


    // 根据 conversationId 获取该会话的所有消息记录
    public static List<Message> getMessagesByConversationId(Context context, String conversationId) {
        // 创建数据库 helper
        MessageHelper dbHelper = new MessageHelper(context);
        // 获取可读的数据库对象
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // 查询 messages 表中的所有数据
        Cursor cursor = db.query("messages", null, "conversationId=?", new String[]{conversationId}, null, null, "time ASC");
        // 创建一个空的 Message 列表
        List<Message> messageList = new ArrayList<>();
        // 遍历查询结果，将每一行数据转换为 Message 对象，并添加到列表中
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String role = cursor.getString(cursor.getColumnIndex("role"));
            @SuppressLint("Range") String content = cursor.getString(cursor.getColumnIndex("content"));
            @SuppressLint("Range") long time = cursor.getLong(cursor.getColumnIndex("time"));
            Message message = new Message(id, conversationId, role, content, new Date(time));
            messageList.add(message);
        }
        // 关闭游标和数据库连接
        cursor.close();
        db.close();
        // 返回 Message 列表
        return messageList;
    }

    // 根据 messageId 删除消息记录
    public static void deleteMessageById(Context context, String messageId) {
        // 创建数据库 helper
        MessageHelper dbHelper = new MessageHelper(context);
        // 获取可写的数据库对象
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // 根据 messageId 删除 messages 表中的数据
        int rows = db.delete("messages", "id=?", new String[]{messageId});
        if (rows > 0) {
            Log.d("MessageHelper", "删除成功");
        } else {
            Log.d("MessageHelper", "删除失败");
        }
        // 关闭数据库连接
        db.close();
    }
}