package com.example.bakingapplicationnanodegree.di.component;

import com.example.bakingapplicationnanodegree.di.modules.RertofitModule;
import com.example.bakingapplicationnanodegree.di.scopes.ApplicationScope;
import com.example.bakingapplicationnanodegree.interfaces.GetBackingRecipe;
import com.example.bakingapplicationnanodegree.ui.MainActivity;

import dagger.Component;

@ApplicationScope
@Component(modules = {RertofitModule.class})
public interface RetrofitComponent {

    GetBackingRecipe getBackingRecipe();
    void inject(MainActivity mainActivity);

}
