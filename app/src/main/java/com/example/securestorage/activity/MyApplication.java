package com.example.securestorage.activity;

import android.app.Application;

import com.example.securestorage.utils.SharedPreferences;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences.init(this);
    }
}
