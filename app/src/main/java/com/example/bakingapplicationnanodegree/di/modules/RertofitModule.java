package com.example.bakingapplicationnanodegree.di.modules;


import com.example.bakingapplicationnanodegree.di.scopes.ApplicationScope;
import com.example.bakingapplicationnanodegree.interfaces.GetBackingRecipe;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class RertofitModule {

    @Provides
    GetBackingRecipe providesGetBackingRecipe(Retrofit retrofit){
        return retrofit.create(GetBackingRecipe.class);
    }

    @Provides
    public Retrofit providesRetrofit(OkHttpClient okHttpClient){
        return new Retrofit.Builder()
                .baseUrl("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Provides
    public OkHttpClient providesOkHttpClient(HttpLoggingInterceptor httpLoggingInterceptor){

        return new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }

    @Provides
    @ApplicationScope
    HttpLoggingInterceptor getHttpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }

}
