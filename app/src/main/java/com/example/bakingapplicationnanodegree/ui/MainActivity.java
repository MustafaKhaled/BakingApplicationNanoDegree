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
import com.example.bakingapplicationnanodegree.interfaces.RecipeClickListener;
import com.example.bakingapplicationnanodegree.models.RecipesListItem;
import com.example.bakingapplicationnanodegree.util.Utilities;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecipeClickListener {
    private static final String TAG = "MainActivity";
    RecipesRecyclerViewAdapter recipeRvAdapter;
    RecyclerView recipesRv;
    ArrayList<RecipesListItem> recipesListModel;
    boolean checkActiveDevice;
    LinearLayoutManager layoutManager;
    Gson gson = new Gson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recipesRv = findViewById(R.id.recipe_rv);
        //Converting Json to Object
        Type listType = new TypeToken<ArrayList<RecipesListItem>>() {}.getType();
        recipesListModel = gson.fromJson(Utilities.readJsonFile("baking.json"), listType);
        Log.d(TAG, "onCreate: Recipes Size "+recipesListModel.size());
        recipeRvAdapter = new RecipesRecyclerViewAdapter(this,recipesListModel,this);
        checkActiveDevice = getResources().getBoolean(R.bool.isTablet);
        if(checkActiveDevice){
            //Tablet device
            layoutManager = new GridLayoutManager(this,3,GridLayoutManager.VERTICAL,false);
        }
        else {
            //Phone device
            layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        }
//        recipesRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recipesRv.setHasFixedSize(true);
        recipesRv.setLayoutManager(layoutManager);
        recipesRv.setAdapter(recipeRvAdapter);


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
