package com.example.bakingapplicationnanodegree.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bakingapplicationnanodegree.R;
import com.example.bakingapplicationnanodegree.interfaces.StepClickListener;
import com.example.bakingapplicationnanodegree.models.StepsItem;
import com.example.bakingapplicationnanodegree.viewholders.RecipeStepViewHolder;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class RecipesStepsAdapter extends RecyclerView.Adapter<RecipeStepViewHolder> {
    Context mContext;
    ArrayList<StepsItem> stepsItems;
    StepClickListener stepClickListener;
    public RecipesStepsAdapter(Context mContext, ArrayList<StepsItem>stepsItems,StepClickListener stepClickListener) {
        this.mContext = mContext;
        this.stepsItems = stepsItems;
        this.stepClickListener = stepClickListener;
    }

    @NonNull
    @Override
    public RecipeStepViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recipe_step_signel_item,viewGroup,false);
        return new RecipeStepViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeStepViewHolder recipeStepViewHolder, int i) {
        recipeStepViewHolder.bind(stepsItems.get(i),stepClickListener);
    }

    @Override
    public int getItemCount() {
        return stepsItems.size();
    }
}
