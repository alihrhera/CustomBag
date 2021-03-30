package com.customs.bag.data.model;


import com.google.firebase.database.Exclude;

import java.util.Date;

public class ChatMessages {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String senderId="";
    String id;
    String messages="";
    String name="";
    long time;

    @Exclude
    public Date getTimeToSort(){
        return new Date(time);
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
