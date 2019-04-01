package com.example.bakingapplicationnanodegree.ui;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.AppComponentFactory;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.bakingapplicationnanodegree.R;
import com.example.bakingapplicationnanodegree.di.component.SharedPreferenceComponent;
import com.example.bakingapplicationnanodegree.fragments.RecipeMediaFragment;
import com.example.bakingapplicationnanodegree.fragments.RecipeStepsFragment;
import com.example.bakingapplicationnanodegree.interfaces.PassingStepDetails;
import com.example.bakingapplicationnanodegree.interfaces.PassingStepInfo;
import com.example.bakingapplicationnanodegree.models.RecipesListItem;
import com.example.bakingapplicationnanodegree.models.StepsItem;
import com.example.bakingapplicationnanodegree.util.MyApplication;
import com.example.bakingapplicationnanodegree.util.MySharedPreference;
import com.example.bakingapplicationnanodegree.util.Utilities;
import com.google.gson.Gson;

import javax.inject.Inject;

@VisibleForTesting
public class RecipeDetailsActivity extends AppCompatActivity implements PassingStepDetails {
    private static final String TAG = "RecipeDetailsActivity";
    RecipesListItem recipesListItem=new RecipesListItem();
    public PassingStepInfo passingStepInfo;
    SharedPreferenceComponent sharedPreferenceComponent;
    @Inject
    MySharedPreference mySharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        String jsonMyObject = null;
        Bundle extras = getIntent().getExtras();
        ((MyApplication)getApplicationContext()).getComponent().inject(this);
        if (extras != null) {
            jsonMyObject = extras.getString("recipe_id");

        recipesListItem = new Gson().fromJson(jsonMyObject, RecipesListItem.class);
        Log.d(TAG, "onCreate: Recipe example " + recipesListItem.getIngredients().size());
        passingStepInfo.passInfo(recipesListItem);
        }
        if(findViewById(R.id.details_parent)!=null){
            Log.d(TAG, "onCreate: Step fragment not null");
            RecipeStepsFragment recipeStepsFragment = new RecipeStepsFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.step_details_fragment,recipeStepsFragment).commit();
            recipeStepsFragment.setStepDetails(recipesListItem.getIngredients().get(0).getIngredient());

        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        invalidateOptionsMenu();
        if(mySharedPreference.getData(recipesListItem.getId()))
            menu.removeItem(R.id.add_to_favorite);
        else 
            menu.removeItem(R.id.remove_from_favorite);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_to_favorite:
                Log.d(TAG, "onOptionsItemSelected: Added as favorite recipe");
                mySharedPreference.setData(recipesListItem.getId(), recipesListItem.getName(), Utilities.prepareIngredientToBeShown(recipesListItem));
                updateWidgetStatus();
                break;

            case R.id.remove_from_favorite:
                Log.d(TAG, "onOptionsItemSelected: Remove from favorite");
                mySharedPreference.removeData();
                updateWidgetStatus();
                break;
            }

        return true;
        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action,menu);
        return true;

    }

    @Override
    public void showDetails(StepsItem stepsItem) {
        RecipeStepsFragment recipeStepsFragment = new RecipeStepsFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.step_details_fragment,recipeStepsFragment).commit();
        recipeStepsFragment.setStepDetails(stepsItem.getDescription());

        RecipeMediaFragment recipeMediaFragment = new RecipeMediaFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.media_fragment,recipeMediaFragment).commit();
        recipeMediaFragment.setVideo(stepsItem.getVideoURL());
    }

    private void updateWidgetStatus(){
        Intent intent = new Intent(this, RecipeWidgetApp.class);
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
        int ids[] = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), RecipeWidgetApp.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        sendBroadcast(intent);
    }
}
