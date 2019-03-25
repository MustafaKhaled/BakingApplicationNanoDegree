package com.example.bakingapplicationnanodegree.util;

import android.app.Application;
import android.content.Context;
import android.print.PrinterId;

public class MyApplication extends Application {
    private static Application instance;
    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        MyApplication.mContext = getApplicationContext();
        instance = this;
        mContext = getApplicationContext();
    }

    public static Context getAppContext(){
        return instance.getApplicationContext();
    }
}
