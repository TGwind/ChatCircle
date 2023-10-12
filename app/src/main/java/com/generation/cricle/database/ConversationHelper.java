package com.generation.cricle.database;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.generation.cricle.entity.Conversation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConversationHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "my_conversation.db"; // 数据库名称
    private static final int DATABASE_VERSION = 1; // 数据库版本号

    public ConversationHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建 conversations 表
        db.execSQL("CREATE TABLE conversations ("
                + "id TEXT, "
                + "label TEXT, "
                + "avatarUrl TEXT, "
                + "lastMessage TEXT, "
                + "lastMessageTime INTEGER)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 如果需要升级数据库，可以在这里实现
    }

    //添加会话记录
    public static void addConversation(Context context, Conversation conversation) {
        // 创建数据库 helper
        ConversationHelper dbHelper = new ConversationHelper(context);
        // 获取可写的数据库对象
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // 创建 ContentValues 对象，用于存储新的会话记录
        ContentValues values = new ContentValues();
        values.put("id", conversation.getConversationId());
        values.put("label", conversation.getLabel());
        values.put("avatarUrl", conversation.getAvatar());
        values.put("lastMessage", conversation.getLastMessage());
        values.put("lastMessageTime", conversation.getLastMessageTime());
        // 插入新的会话记录
        long id = db.insert("conversations", null, values);
        if (id != -1) {
            Log.d(TAG,"插入成功"+id);
        }else {
            Log.d(TAG,"插入失败");
        }
        // 关闭数据库连接
        db.close();
    }
    public static List<Conversation> getAllConversations(Context context) {
        // 创建数据库 helper
        ConversationHelper dbHelper = new ConversationHelper(context);
        // 获取可读的数据库对象
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // 查询 conversations 表中的所有数据
        Cursor cursor = db.query("conversations", null, null, null, null, null, null);
        // 创建一个空的 Conversation 列表
        List<Conversation> conversationList = new ArrayList<>();
        // 遍历查询结果，将每一行数据转换为 Conversation 对象，并添加到列表中
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String label = cursor.getString(cursor.getColumnIndex("label"));
            @SuppressLint("Range") String avatarUrl = cursor.getString(cursor.getColumnIndex("avatarUrl"));
            @SuppressLint("Range") String lastMessage = cursor.getString(cursor.getColumnIndex("lastMessage"));
            @SuppressLint("Range") long lastMessageTime = cursor.getLong(cursor.getColumnIndex("lastMessageTime"));
            Conversation conversation = new Conversation(id, label, avatarUrl, lastMessage, new Date(lastMessageTime));
            conversationList.add(conversation);
        }
        // 关闭游标和数据库连接
        cursor.close();
        db.close();
        // 返回 Conversation 列表
        return conversationList;
    }

    //根据id删除会话
    public static void deleteConversationById(Context context, String conversationId) {
        // 创建数据库 helper
        ConversationHelper dbHelper = new ConversationHelper(context);
        MessageHelper messageHelper = new MessageHelper(context);
        // 获取可写的数据库对象
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        SQLiteDatabase messageDb = messageHelper.getReadableDatabase();
//         根据 conversationId 删除 conversations 表中的数据
        int rows = db.delete("conversations", "id=?", new String[]{conversationId});
        int rows2 = messageDb.delete("messages", "conversationId=?", new String[]{conversationId});

        if (rows > 0&& rows2>0) {
            Log.d(TAG, "删除成功");
        } else {
            Log.d(TAG, "删除失败");
        }
        // 关闭数据库连接
        db.close();
    }
    public static void updateConversationLabel(Context context, String conversationId, String newLabel) {
        // 创建数据库 helper
        ConversationHelper dbHelper = new ConversationHelper(context);
        // 获取可写的数据库对象
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // 创建 ContentValues 对象，用于更新会话记录
        ContentValues values = new ContentValues();
        values.put("label", newLabel);
        // 根据 conversationId 更新 conversations 表中的数据
        int rows = db.update("conversations", values, "id=?", new String[]{conversationId});
        if (rows > 0) {
            Log.d(TAG, "修改成功");
        } else {
            Log.d(TAG, "修改失败");
        }
        // 关闭数据库连接
        db.close();
    }



}
