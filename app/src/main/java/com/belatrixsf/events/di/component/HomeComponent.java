package com.belatrixsf.events.di.component;

import com.belatrixsf.events.di.module.HomeModule;
import com.belatrixsf.events.di.scope.UIScope;
import com.belatrixsf.events.presentation.ui.fragments.HomeFragment;

import dagger.Subcomponent;

/**
 * Created by dvelasquez on 2/23/17.
 */
@UIScope
@Subcomponent(
        modules = {
                HomeModule.class,
        })
public interface HomeComponent {

    void inject(HomeFragment fragment);
}
