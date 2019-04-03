package com.example.bakingapplicationnanodegree.util;

import android.app.Application;
import android.content.Context;

import com.example.bakingapplicationnanodegree.di.component.DaggerRetrofitComponent;
import com.example.bakingapplicationnanodegree.di.component.DaggerSharedPreferenceComponent;
import com.example.bakingapplicationnanodegree.di.component.RetrofitComponent;
import com.example.bakingapplicationnanodegree.di.component.SharedPreferenceComponent;
import com.example.bakingapplicationnanodegree.di.modules.RertofitModule;
import com.example.bakingapplicationnanodegree.di.modules.SharedPrefModule;

public class MyApplication extends Application {
    private static Application instance;
    private static Context mContext;
    SharedPreferenceComponent sharedPreferenceComponent;
    RetrofitComponent retrofitComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        MyApplication.mContext = getApplicationContext();
        instance = this;
        mContext = getApplicationContext();
        sharedPreferenceComponent  = DaggerSharedPreferenceComponent.builder().sharedPrefModule(new SharedPrefModule(getApplicationContext()    )).build();
        retrofitComponent = DaggerRetrofitComponent.builder().rertofitModule(new RertofitModule()).build();
    }

    public SharedPreferenceComponent getComponent(){
        return sharedPreferenceComponent;
    }

    public RetrofitComponent getRetrofitComponent(){return retrofitComponent;}

    public static Context getAppContext(){
        return instance.getApplicationContext();
    }
}
