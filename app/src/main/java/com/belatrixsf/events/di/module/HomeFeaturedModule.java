package com.belatrixsf.events.di.module;

import com.belatrixsf.events.di.scope.UIScope;
import com.belatrixsf.events.presentation.presenters.HomeFeaturedPresenter;
import com.belatrixsf.events.presentation.ui.fragments.HomeFeaturedFragment;

import dagger.Module;
import dagger.Provides;

@Module
public class HomeFeaturedModule {

    private final HomeFeaturedFragment fragment;
    private final HomeFeaturedPresenter.View view;

    public HomeFeaturedModule(HomeFeaturedFragment fragment) {
        this.fragment = fragment;
        this.view = fragment;
    }


    @Provides
    @UIScope
    public HomeFeaturedFragment eventFeaturedFragment() {
        return fragment;
    }


    @Provides
    @UIScope
    public HomeFeaturedPresenter.View view() {
        return view;
    }


}