package com.customs.bag.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.customs.bag.data.model.Post;

public class DataManger {

    private DataManger() {
    }

    private static final DataManger obj = new DataManger();

    public synchronized static DataManger getInstance() {
        return obj;
    }

    private Context context;
    private SharedPreferences shp;

    public void setContext(Context context) {
        this.context = context;
        shp = context.getSharedPreferences("CustomsBag", Context.MODE_PRIVATE);
    }

    public final static String Url = "https://era-apps.com/custom_bag/api/";
    private Post postToMove;
    /*this is premium Mod for user has sub plan no ads mor functions*/
    public static final int Premium = 10000;

    /*this is normal Mod for user has no sub plan alot OF ads less functions*/
    public static final int Normal = 1000;


    public static final int Trial = 5000;

    //return the user Mode defualt normal
    int userMod = Normal;

    public int getUserMode() {
        return userMod;
    }


    public Post getPostToMove() {
        return postToMove;
    }

    public void setPostToMove(Post postToMove) {
        this.postToMove = postToMove;
    }


    public String getPhone() {
        return shp.getString("phone", "");
    }

    public String getName() {
        return shp.getString("name", "");
    }

    public void setNameAndPhone(String name, String phone) {
        shp.edit().putString("name", name).apply();
        shp.edit().putString("phone", phone).apply();
        shp.edit().putBoolean("reg", true).apply();
    }

    public boolean isUser() {
        return shp.getBoolean("reg", false);
    }


    private boolean chatIsOpen;

    public boolean isChatOpen() {
        return chatIsOpen;
    }

    public void setChatOpen(boolean chatIsOpen) {
        this.chatIsOpen = chatIsOpen;
    }
}




