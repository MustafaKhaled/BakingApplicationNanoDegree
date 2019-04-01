package com.example.bakingapplicationnanodegree.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bakingapplicationnanodegree.R;
import com.example.bakingapplicationnanodegree.ui.RecipeMediaDetailsPhone;

public class RecipeStepsFragment extends Fragment {
    private static final String TAG = "RecipeStepsFragment";
    TextView recipeDesc;
    String mStep,mVideoUrl;
    public RecipeStepsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: Creating Recipe details fragment");
          View  view= inflater.inflate(R.layout.recipe_step_details_fragment,container,false);

        recipeDesc = view.findViewById(R.id.recipe_description);


        if(getResources().getBoolean(R.bool.isTablet))
            recipeDesc.setText(mStep);

        else {
            RecipeMediaDetailsPhone recipeMediaDetailsPhone = (RecipeMediaDetailsPhone) getActivity();
            recipeDesc.setText(recipeMediaDetailsPhone.setChosenData());
        }
        return view;
    }


    public void setStepDetails(String step){
        Log.d(TAG, "setStepDetails: set the text to "+ step);
        this.mStep = step;
    }




}
