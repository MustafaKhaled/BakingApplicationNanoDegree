package com.example.bakingapplicationnanodegree.di.component;

import com.example.bakingapplicationnanodegree.di.modules.SharedPrefModule;
import com.example.bakingapplicationnanodegree.di.scopes.ApplicationScope;
import com.example.bakingapplicationnanodegree.ui.RecipeDetailsActivity;

import dagger.Component;

@ApplicationScope
@Component(modules = {SharedPrefModule.class})
public interface SharedPreferenceComponent {
    void inject(RecipeDetailsActivity recipeDetailsActivity);

}
