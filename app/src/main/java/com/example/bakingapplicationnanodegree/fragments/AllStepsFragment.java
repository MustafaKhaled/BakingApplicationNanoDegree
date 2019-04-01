package com.example.bakingapplicationnanodegree.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bakingapplicationnanodegree.R;
import com.example.bakingapplicationnanodegree.adapters.RecipesStepsAdapter;
import com.example.bakingapplicationnanodegree.interfaces.PassingStepDetails;
import com.example.bakingapplicationnanodegree.interfaces.PassingStepInfo;
import com.example.bakingapplicationnanodegree.interfaces.StepClickListener;
import com.example.bakingapplicationnanodegree.models.RecipesListItem;
import com.example.bakingapplicationnanodegree.ui.RecipeDetailsActivity;
import com.example.bakingapplicationnanodegree.ui.RecipeMediaDetailsPhone;
import com.example.bakingapplicationnanodegree.util.Utilities;

public class AllStepsFragment extends Fragment implements  StepClickListener, PassingStepInfo {
    private static final String TAG = "StepRecyclerViewFragmen";
    PassingStepDetails fragmentToActivity;
    RecipesListItem mRecipesListItem;
    RecipesStepsAdapter stepsAdapter;
    RecyclerView.LayoutManager layoutManager;
    NestedScrollView mNestedScrollView;
    TextView ingredientTv;
    RecyclerView stepsRV;
    public AllStepsFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        context = getActivity();
        if (context != null) {
            fragmentToActivity = (PassingStepDetails) context;
            ((RecipeDetailsActivity)context).passingStepInfo = this;
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.steps_recyclerview_fragment,container,false);
         ingredientTv = view.findViewById(R.id.ingredient_values);
         stepsRV = view.findViewById(R.id.steps_rv);
         mNestedScrollView = view.findViewById(R.id.parent_nested_scrollview);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.action, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    private void initView(RecipesListItem recipesListItem) {
        Log.d(TAG, "initView: After init "+ recipesListItem);
        layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        stepsAdapter = new RecipesStepsAdapter(getActivity(),recipesListItem.getSteps(),this);
        ingredientTv.setText(Utilities.prepareIngredientToBeShown(recipesListItem).toString());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL);
        stepsRV.addItemDecoration(dividerItemDecoration) ;
        stepsRV.setHasFixedSize(true);
        stepsRV.setLayoutManager(layoutManager);
        stepsRV.setAdapter(stepsAdapter);
    }

    @Override
    public void StepClick(View view, int position) {
        Log.d(TAG, "StepClick: item #"+position+" clicked");
        if(getResources().getBoolean(R.bool.isTablet)){
            fragmentToActivity.showDetails(mRecipesListItem.getSteps().get(position));
        }
        else{
            Intent intent = new Intent(getContext(), RecipeMediaDetailsPhone.class);
            intent.putParcelableArrayListExtra("steps_list",mRecipesListItem.getSteps());
            intent.putExtra("step_id",position);
            startActivity(intent);
        }
    }

    @Override
    public void passInfo(RecipesListItem recipesListItem) {
        Log.d(TAG, "passInfo: "+recipesListItem);
        this.mRecipesListItem = recipesListItem;
        initView(recipesListItem);
    }
}
