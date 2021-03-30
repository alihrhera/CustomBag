package com.customs.bag.data.model;

public class Post {
    int id;
    String title;
    String content;
    String image;
    boolean states;
    int times;
    long time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }
    public String getOnlineUrl(){
        return "https://era-apps.com/custom_bag/api/"+image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isStates() {
        return states;
    }

    public void setStates(boolean states) {
        this.states = states;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
