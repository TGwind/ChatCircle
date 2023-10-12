package com.generation.cricle.entity;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Conversation  {
    private String conversationId; // 对话ID
//    private List<User> participants; // 参与者列表
    private String label;    //会话名称
    private String avatar = "http://myblog.over2022.top/20230614224852.jpg";
    private String lastMessage = "你好，我是ChatGPT";
    private Date lastMessageTime = new Date();

    public Conversation(String label) {
        this.conversationId = UUID.randomUUID().toString();
        this.label = label;
    }

    public Conversation(String conversationId, String label, String avatar, String lastMessage, Date lastMessageTime) {
        this.conversationId = conversationId;
        this.label = label;
        this.avatar = avatar;
        this.lastMessage = lastMessage;
        this.lastMessageTime = lastMessageTime;
    }

    // Getters and setters
    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getLabel() {
        return label;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setLastMessageTime(Date lastMessageTime) {
        this.lastMessageTime = lastMessageTime;
    }

    public String getLastMessageTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm");
        String formattedDate = formatter.format(lastMessageTime);
        return formattedDate;
    }
}


