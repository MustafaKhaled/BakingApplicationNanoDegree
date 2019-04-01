package com.example.bakingapplicationnanodegree;


import android.support.test.espresso.Espresso;
import android.support.test.espresso.Espresso.*;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.bakingapplicationnanodegree.fragments.AllStepsFragment;
import com.example.bakingapplicationnanodegree.ui.MainActivity;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class RecipeHomeActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule =
            new ActivityTestRule<MainActivity>(MainActivity.class);
//
//    @Rule
//    public FragmentTestRule<AllStepsFragment> allStepsFragmentFragmentTestRule =
//            new FragmentTestRule<>(AllStepsFragment.class);

    @Test
    public void chooseRecipe(){
        Espresso.onView(ViewMatchers.withId(R.id.recipe_rv))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));

        Espresso.onView(ViewMatchers.withId(R.id.steps_fragment)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }




}
