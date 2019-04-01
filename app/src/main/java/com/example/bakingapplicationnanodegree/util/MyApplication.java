package com.example.bakingapplicationnanodegree.util;

import android.app.Application;
import android.content.Context;

import com.example.bakingapplicationnanodegree.di.component.DaggerSharedPreferenceComponent;
import com.example.bakingapplicationnanodegree.di.component.SharedPreferenceComponent;
import com.example.bakingapplicationnanodegree.di.modules.SharedPrefModule;

public class MyApplication extends Application {
    private static Application instance;
    private static Context mContext;
    SharedPreferenceComponent sharedPreferenceComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        MyApplication.mContext = getApplicationContext();
        instance = this;
        mContext = getApplicationContext();
        sharedPreferenceComponent  = DaggerSharedPreferenceComponent.builder().sharedPrefModule(new SharedPrefModule(getApplicationContext()    )).build();

    }

    public SharedPreferenceComponent getComponent(){
        return sharedPreferenceComponent;
    }

    public static Context getAppContext(){
        return instance.getApplicationContext();
    }
}
