package com.example.bakingapplicationnanodegree.ui;

import android.content.res.Configuration;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.bakingapplicationnanodegree.R;
import com.example.bakingapplicationnanodegree.fragments.RecipeMediaFragment;
import com.example.bakingapplicationnanodegree.fragments.RecipeStepsFragment;
import com.example.bakingapplicationnanodegree.interfaces.PassingStepInfo;
import com.example.bakingapplicationnanodegree.models.RecipesListItem;
import com.example.bakingapplicationnanodegree.models.StepsItem;
import com.google.gson.Gson;

import java.util.ArrayList;

public class RecipeMediaDetailsPhone extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "RecipeMediaDetailsPhone";
    Button nextButton,previousButton;
    ConstraintSet set = new ConstraintSet();
    int currentStep;
    ConstraintLayout parentConstraint;
    FrameLayout stepContainer,mediaContainer;
    LinearLayout navigationLayout;
    PassingStepInfo passingStepInfo;
    RecipeStepsFragment recipeStepsDesc;
    RecipeMediaFragment recipeMediaFragment;
    ArrayList<StepsItem> stepsArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_media_details_phone);
        Log.d(TAG, "onCreate: orientation");
        parentConstraint = findViewById(R.id.parent_constraints);
        stepContainer = findViewById(R.id.recipe_steps_container);
        mediaContainer = findViewById(R.id.recipe_media_container);
        navigationLayout = findViewById(R.id.navigation_layout);
        nextButton = findViewById(R.id.next_btn);
        previousButton  = findViewById(R.id.previous_btn);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //clone the layout in order to use it later.
        set.clone(parentConstraint);
        //Get the selected step from list
        currentStep = getIntent().getIntExtra("step_id",0);
        stepsArrayList =  getIntent().getParcelableArrayListExtra("steps_list");
        recipeStepsDesc = new RecipeStepsFragment();
        recipeMediaFragment = new RecipeMediaFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();

        //Add the 2 fragments
        fragmentManager.beginTransaction().add(R.id.recipe_steps_container,recipeStepsDesc).commit();
        fragmentManager.beginTransaction().add(R.id.recipe_media_container,recipeMediaFragment).commit();
        Log.d(TAG, "onCreate: first item "+ stepsArrayList.get(currentStep).getDescription());
        checkLastStep(currentStep);
        nextButton.setOnClickListener(this);
        previousButton.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) mediaContainer.getLayoutParams();
        super.onConfigurationChanged(newConfig);
        Log.d(TAG, "onConfigurationChanged: landscape");
        //We override this function to detect landscape view, to show it full screen mode
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            if(stepsArrayList.get(currentStep).getVideoURL().equals("")){
                mediaContainer.setVisibility(View.GONE);
                navigationLayout.setVisibility(View.VISIBLE);
                stepContainer.setVisibility(View.VISIBLE);

            }
            else{
                navigationLayout.setVisibility(View.GONE);
                stepContainer.setVisibility(View.GONE);
                params.width = params.MATCH_CONSTRAINT;
                params.height = params.MATCH_CONSTRAINT;

            }
            getSupportActionBar().hide();

        }

        else if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.d(TAG, "onConfigurationChanged: protrait");
            getSupportActionBar().show();
            navigationLayout.setVisibility(View.VISIBLE);
            stepContainer.setVisibility(View.VISIBLE);
            params.width = params.MATCH_CONSTRAINT;
            params.height = params.MATCH_CONSTRAINT;
        }
    }

    public String setChosenVideo(){
        checkVideoUrl(stepsArrayList.get(currentStep).getVideoURL());
        return stepsArrayList.get(currentStep).getVideoURL();
    }

    public String setChosenData(){
        return stepsArrayList.get(currentStep).getDescription();
    }

    @Override
    public void onClick(View v) {

        RecipeStepsFragment mRecipeStepFragment = new RecipeStepsFragment();
        RecipeMediaFragment mRecipeMediaFragment = new RecipeMediaFragment();
        Button b = (Button) v;
        switch (b.getId()){
            case R.id.next_btn:
                Log.d(TAG, "onClick: Next button is clicked");
                currentStep = currentStep+1;
                Log.d(TAG, "onClick: The current step is: "+currentStep);
                checkLastStep(currentStep);
                checkVideoUrl(stepsArrayList.get(currentStep).getVideoURL());

                getSupportFragmentManager().beginTransaction().replace(R.id.recipe_steps_container,mRecipeStepFragment).commit();
                getSupportFragmentManager().beginTransaction().replace(R.id.recipe_media_container,mRecipeMediaFragment).commit();

                mRecipeStepFragment.setStepDetails(stepsArrayList.get(currentStep).getDescription());
                mRecipeMediaFragment.setVideo(stepsArrayList.get(currentStep).getVideoURL());
                break;

            case R.id.previous_btn:
                Log.d(TAG, "onClick: Previous button is clicked");
                currentStep = currentStep-1;
                Log.d(TAG, "onClick: The current step is: "+currentStep);
                checkLastStep(currentStep);
                checkVideoUrl(stepsArrayList.get(currentStep).getVideoURL());

                getSupportFragmentManager().beginTransaction().replace(R.id.recipe_steps_container,mRecipeStepFragment).commit();
                getSupportFragmentManager().beginTransaction().replace(R.id.recipe_media_container,mRecipeMediaFragment).commit();

                mRecipeStepFragment.setStepDetails(stepsArrayList.get(currentStep).getDescription());
                mRecipeMediaFragment.setVideo(stepsArrayList.get(currentStep).getVideoURL());

                break;
        }
    }
    private void checkVideoUrl(String url){
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) stepContainer.getLayoutParams();
        ConstraintLayout.LayoutParams params1 = (ConstraintLayout.LayoutParams) mediaContainer.getLayoutParams();
        if(url.equals("")){
            Log.d(TAG, "setChosenVideo: No video for this step, hide playerView");
            mediaContainer.setVisibility(View.GONE);
            params.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
            params.bottomToBottom = 0;
            params.height = params.MATCH_CONSTRAINT;
        }
        else{
            Log.d(TAG, "setChosenVideo: Valid video for this step, show playerView");
            mediaContainer.setVisibility(View.VISIBLE);
            stepContainer.setVisibility(View.GONE);
            params1.width = params.MATCH_CONSTRAINT;
            params1.height = params.MATCH_CONSTRAINT;
            //Apply cloned Layout
            set.applyTo(parentConstraint);
        }
    }

    private void checkLastStep(int currentStep){
        if(currentStep == stepsArrayList.size()-1){
            nextButton.setEnabled(false);
            previousButton.setEnabled(true);
        }
        else if(currentStep == 0){
            nextButton.setEnabled(true);
            previousButton.setEnabled(false);
        }
        else{
            nextButton.setEnabled(true);
            previousButton.setEnabled(true);
        }

    }

}
