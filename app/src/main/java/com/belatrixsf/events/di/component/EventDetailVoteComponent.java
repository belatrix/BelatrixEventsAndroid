package com.belatrixsf.events.di.component;

import com.belatrixsf.events.di.module.EventDetailVoteModule;
import com.belatrixsf.events.di.scope.UIScope;
import com.belatrixsf.events.presentation.ui.fragments.EventDetailVoteFragment;

import dagger.Subcomponent;

/**
 * Created by dvelasquez on 2/23/17.
 */
@UIScope
@Subcomponent(
        modules = {
                EventDetailVoteModule.class,
        })
public interface EventDetailVoteComponent {

    void inject(EventDetailVoteFragment fragment);
}
