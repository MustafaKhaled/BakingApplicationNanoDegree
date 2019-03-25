package com.example.bakingapplicationnanodegree.viewholders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.bakingapplicationnanodegree.R;
import com.example.bakingapplicationnanodegree.interfaces.RecipeClickListener;
import com.example.bakingapplicationnanodegree.models.RecipesListItem;

public class RecipesViewHolder extends RecyclerView.ViewHolder {
    TextView recipeName,servingNum;
    public RecipesViewHolder(@NonNull View itemView) {
        super(itemView);
        recipeName = itemView.findViewById(R.id.recipe_tv);
        servingNum = itemView.findViewById(R.id.serving_tv);
    }

    public void bind(RecipesListItem recipesListItem, final RecipeClickListener recipeClickListener){
        recipeName.setText(recipesListItem.getName());
        servingNum.setText(recipesListItem.getServings()+"");
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recipeClickListener.onClickRecipe(v,getAdapterPosition());
            }
        });
    }


}
