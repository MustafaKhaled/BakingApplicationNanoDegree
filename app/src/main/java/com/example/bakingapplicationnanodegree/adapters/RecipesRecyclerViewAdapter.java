package com.example.bakingapplicationnanodegree.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bakingapplicationnanodegree.R;
import com.example.bakingapplicationnanodegree.interfaces.RecipeClickListener;
import com.example.bakingapplicationnanodegree.models.RecipesListItem;
import com.example.bakingapplicationnanodegree.ui.MainActivity;
import com.example.bakingapplicationnanodegree.viewholders.RecipesViewHolder;

import java.util.ArrayList;

import retrofit2.Callback;

public class RecipesRecyclerViewAdapter extends RecyclerView.Adapter<RecipesViewHolder> {
    Context mContext;
    ArrayList<RecipesListItem> recipesListItems;
    RecipeClickListener mRecipeClickListener;
    public RecipesRecyclerViewAdapter(Context mContext, ArrayList<RecipesListItem> recipesListItems, RecipeClickListener recipeClickListener) {
        this.mContext = mContext;
        this.recipesListItems = recipesListItems;
        this.mRecipeClickListener = recipeClickListener;
    }


    @NonNull
    @Override
    public RecipesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.recipe_list_item,viewGroup,false);
        return new RecipesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesViewHolder recipesViewHolder, int i) {
        recipesViewHolder.bind(recipesListItems.get(i),mRecipeClickListener);
    }


    @Override
    public int getItemCount() {
        return recipesListItems.size();
    }
}
