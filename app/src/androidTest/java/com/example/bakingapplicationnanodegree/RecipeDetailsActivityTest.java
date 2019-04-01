package com.example.bakingapplicationnanodegree;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.bakingapplicationnanodegree.ui.MainActivity;
import com.example.bakingapplicationnanodegree.ui.RecipeDetailsActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;

@RunWith(AndroidJUnit4.class)
public class RecipeDetailsActivityTest {
    private static final String EXTRA_RECIPE_ID_KEY = "recipe_id";
    @Rule
    public IntentsTestRule<MainActivity> intentsTestRule
            = new IntentsTestRule<>(MainActivity.class);

    @Test
    public void clickOnRecyclerViewItem_runsRecipeDetailsActivityIntent() {

        onView(ViewMatchers.withId(R.id.recipe_rv)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        Intents.intended(
                hasExtraWithKey(EXTRA_RECIPE_ID_KEY)
        );
    }
}
