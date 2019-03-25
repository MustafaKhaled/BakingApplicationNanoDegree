package com.example.bakingapplicationnanodegree.ui;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.bakingapplicationnanodegree.R;
import com.example.bakingapplicationnanodegree.adapters.RecipesStepsAdapter;
import com.example.bakingapplicationnanodegree.interfaces.StepClickListener;
import com.example.bakingapplicationnanodegree.models.RecipesListItem;
import com.google.gson.Gson;

import java.util.Objects;

public class RecipeDetailsActivity extends AppCompatActivity implements StepClickListener {
    private static final String TAG = "RecipeDetailsActivity";
    private final String LIST_POSITION_KEY="list_item_position";
    NestedScrollView mNestedScrollView;
    RecipesListItem recipesListItem;
    RecipesStepsAdapter stepsAdapter;
    LinearLayoutManager layoutManager;
    TextView ingredientsTv,ingredientValues;
    RecyclerView stepsRV;
    int currentVisiblePosition = 0;
    int saveListPosition, currentOffset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        ingredientsTv = findViewById(R.id.ingredient_tv);
        ingredientValues = findViewById(R.id.ingredient_values);
        mNestedScrollView = findViewById(R.id.parent_nested_scrollview);
        stepsRV = findViewById(R.id.steps_rv);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        String jsonMyObject=null;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            jsonMyObject = extras.getString("recipe_id");
        }
        recipesListItem = new Gson().fromJson(jsonMyObject, RecipesListItem.class);
        Log.d(TAG, "onCreate: Recipe example "+ recipesListItem.getIngredients().size());
        stepsAdapter = new RecipesStepsAdapter(this,recipesListItem.getSteps(),this);

        initView();

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntArray("SCROLL_POSITION",
                new int[]{ mNestedScrollView.getScrollX(), mNestedScrollView.getScrollY()});
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        final int[] position = savedInstanceState.getIntArray("SCROLL_POSITION");
        if(position != null)
            mNestedScrollView.post(new Runnable() {
                public void run() {
                    mNestedScrollView.scrollTo(position[0], position[1]);
                }
            });
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

    private void initView() {
        StringBuilder fullIngredients=new StringBuilder();
        for (int i=0; i<recipesListItem.getIngredients().size();i++) {
            if (i != recipesListItem.getIngredients().size() - 1) {
                fullIngredients.append(recipesListItem.getIngredients().get(i).getIngredient());
                fullIngredients.append(" - ");
            }
            else{
                fullIngredients.append(recipesListItem.getIngredients().get(i).getIngredient());
            }
        }
        ingredientValues.setText(fullIngredients.toString());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,layoutManager.getOrientation());
        stepsRV.addItemDecoration(dividerItemDecoration) ;
        stepsRV.setHasFixedSize(true);
        stepsRV.setLayoutManager(layoutManager);
        stepsRV.setAdapter(stepsAdapter);
    }

    @Override
    public void StepClick(View view, int position) {
        Log.d(TAG, "StepClick: item #"+position+" clicked");
        if(getResources().getBoolean(R.bool.isTablet)){

        }
        else{
            Intent intent = new Intent(RecipeDetailsActivity.this,RecipeMediaDetailsPhone.class);
            intent.putParcelableArrayListExtra("steps_list",recipesListItem.getSteps());
            intent.putExtra("step_id",position);
            startActivity(intent);
        }
    }
}
