package com.belatrixsf.events.di.module;

import com.belatrixsf.events.di.scope.UIScope;
import com.belatrixsf.events.presentation.presenters.HomePresenter;
import com.belatrixsf.events.presentation.ui.fragments.HomeFragment;

import dagger.Module;
import dagger.Provides;

@Module
public class HomeModule {

    private final HomeFragment fragment;
    private final HomePresenter.View view;

    public HomeModule(HomeFragment fragment) {
        this.fragment = fragment;
        this.view = fragment;
    }


    @Provides
    @UIScope
    public HomeFragment homeFragment() {
        return fragment;
    }


    @Provides
    @UIScope
    public HomePresenter.View view() {
        return view;
    }


}