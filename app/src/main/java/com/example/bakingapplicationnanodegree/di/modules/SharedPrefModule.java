package com.example.bakingapplicationnanodegree.di.modules;


import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.bakingapplicationnanodegree.di.scopes.ApplicationScope;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;

@Module
public class SharedPrefModule {

    Context mContext;

    public SharedPrefModule(Context context) {
        this.mContext = context;
    }

    @Provides
    @ApplicationScope
    public Context providesContext(){
        return mContext;
    }

    @Provides
    @ApplicationScope
    SharedPreferences providesSharedPreference(Context context){
        return context.getSharedPreferences("Recipe_file",Context.MODE_PRIVATE);
    }

}
