package com.generation.cricle.entity;


import java.util.Date;
import java.util.UUID;

public class Message {
    private String messageId; // ID
    private String conversationId; //会话ID
    private String role; // 发送者角色，例如用户或机器人
    private String content; // 消息内容
    private Date time; // 发送时间

    public Message(String messageId, String conversationId, String role, String content, Date time) {
        this.messageId = messageId;
        this.conversationId = conversationId;
        this.role = role;
        this.content = content;
        this.time = time;
    }

    public Message(String conversationId, String content, String role ) {
        this.messageId = UUID.randomUUID().toString();
        this.conversationId = conversationId;
        this.role = role;
        this.content = content;
        this.time = new Date();
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getConversationId() {
        return conversationId;
    }
}
