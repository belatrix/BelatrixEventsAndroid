package com.belatrixsf.events.di.module;

import com.belatrixsf.events.di.scope.UIScope;
import com.belatrixsf.events.presentation.presenters.EventDetailVotePresenter;
import com.belatrixsf.events.presentation.presenters.HomeFeaturedPresenter;
import com.belatrixsf.events.presentation.ui.fragments.EventDetailVoteFragment;
import com.belatrixsf.events.presentation.ui.fragments.HomeFeaturedFragment;

import dagger.Module;
import dagger.Provides;

@Module
public class EventDetailVoteModule {

    private final EventDetailVoteFragment fragment;
    private final EventDetailVotePresenter.View view;

    public EventDetailVoteModule(EventDetailVoteFragment fragment) {
        this.fragment = fragment;
        this.view = fragment;
    }


    @Provides
    @UIScope
    public EventDetailVoteFragment eventDetailVoteFragment() {
        return fragment;
    }


    @Provides
    @UIScope
    public EventDetailVotePresenter.View view() {
        return view;
    }


}