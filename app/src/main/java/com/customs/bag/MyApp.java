package com.customs.bag;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;
import com.customs.bag.util.DataManger;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AndroidNetworking.initialize(getApplicationContext());
        DataManger.getInstance().setContext(getApplicationContext());
    }
}
