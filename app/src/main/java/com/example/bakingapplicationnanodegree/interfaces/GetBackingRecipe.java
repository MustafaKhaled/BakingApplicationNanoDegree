package com.example.bakingapplicationnanodegree.interfaces;

import com.example.bakingapplicationnanodegree.models.RecipesListItem;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetBackingRecipe {

    @GET("baking.json")
    Call<ArrayList<RecipesListItem>> getRecipes();
}
