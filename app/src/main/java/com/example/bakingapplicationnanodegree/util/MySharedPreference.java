package com.example.bakingapplicationnanodegree.util;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.bakingapplicationnanodegree.models.RecipesListItem;

import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.inject.Inject;

public class MySharedPreference {
    private static final String TAG = "MySharedPreference";

    private SharedPreferences mySharedPreference;

    @Inject
    public MySharedPreference(SharedPreferences mySharedPreference) {
        this.mySharedPreference = mySharedPreference;
    }

    public void setData(int key, String recipeName,String  recipeInfo){
        mySharedPreference.edit().putInt("recipe_id",key).apply();
        mySharedPreference.edit().putString("recipe_name",recipeName).apply();
        mySharedPreference.edit().putString("recipe_ing",recipeInfo).apply();

    }

    public void removeData(){
        Log.d(TAG, "removeData: remove item ");
        mySharedPreference.edit().remove("recipe_id").apply();
        mySharedPreference.edit().remove("recipe_name").apply();
        mySharedPreference.edit().remove("recipe_ing").apply();
    }
    
    public boolean getData(int key){

        if(mySharedPreference.getInt("recipe_id",0)==key){
            Log.d(TAG, "getData: Found item");
            return true;
        }
        else{
            Log.d(TAG, "getData: Not found, add to favorite");
            return false;
        }
    }

    public String getRecipeName(String key){
        return mySharedPreference.getString("recipe_name","");
    }
    public String getRecipeIngredients(String key){
        return mySharedPreference.getString("recipe_ing","");
    }
}
