package com.example.bakingapplicationnanodegree.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.bakingapplicationnanodegree.R;
import com.example.bakingapplicationnanodegree.adapters.RecipesRecyclerViewAdapter;
import com.example.bakingapplicationnanodegree.di.component.RetrofitComponent;
import com.example.bakingapplicationnanodegree.interfaces.GetBackingRecipe;
import com.example.bakingapplicationnanodegree.interfaces.RecipeClickListener;
import com.example.bakingapplicationnanodegree.models.RecipesListItem;
import com.example.bakingapplicationnanodegree.util.MyApplication;
import com.example.bakingapplicationnanodegree.util.Utilities;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecipeClickListener {
    private static final String TAG = "MainActivity";

    @Inject
    GetBackingRecipe getBackingRecipe;
    RecipesRecyclerViewAdapter recipeRvAdapter;
    RecyclerView recipesRv;
    ArrayList<RecipesListItem> recipesListModel;
    boolean checkActiveDevice;
    LinearLayoutManager layoutManager;
    RetrofitComponent retrofitComponent;
    Gson gson = new Gson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final RecipeClickListener recipeClickListener = this;
        recipesRv = findViewById(R.id.recipe_rv);
        ((MyApplication) getApplication()).getRetrofitComponent().inject(this);
        getBackingRecipe.getRecipes().enqueue(new Callback<ArrayList<RecipesListItem>>() {
            @Override
            public void onResponse(Call<ArrayList<RecipesListItem>> call, Response<ArrayList<RecipesListItem>> response) {
                Log.d(TAG, "onResponse: "+response.body().size());
                recipesListModel = response.body();
                recipeRvAdapter = new RecipesRecyclerViewAdapter(MainActivity.this,recipesListModel,recipeClickListener);
                checkActiveDevice = getResources().getBoolean(R.bool.isTablet);
                if(checkActiveDevice){
                    //Tablet device
                    layoutManager = new GridLayoutManager(MainActivity.this,3,GridLayoutManager.VERTICAL,false);
                }
                else {
                    //Phone device
                    layoutManager = new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false);
                }
//        recipesRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
                recipesRv.setHasFixedSize(true);
                recipesRv.setLayoutManager(layoutManager);
                recipesRv.setAdapter(recipeRvAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<RecipesListItem>> call, Throwable t) {

            }
        });
        //Converting Json to Object



    }


    @Override
    public void onClickRecipe(View view, int position) {
        Log.d(TAG, "onClickRecipe: item #"+position+" Clicked");
        Intent intent = new Intent(MainActivity.this,RecipeDetailsActivity.class);
        intent.putExtra("recipe_id",new Gson().toJson(recipesListModel.get(position)));
        Log.d(TAG, "onCreate: Recipe example "+ recipesListModel.get(0).getIngredients().size());
        startActivity(intent);
    }
}
