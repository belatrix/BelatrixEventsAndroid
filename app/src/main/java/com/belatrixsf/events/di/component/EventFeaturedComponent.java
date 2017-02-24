package com.belatrixsf.events.di.component;

import com.belatrixsf.events.di.module.HomeFeaturedModule;
import com.belatrixsf.events.di.scope.UIScope;
import com.belatrixsf.events.presentation.ui.fragments.HomeFeaturedFragment;

import dagger.Subcomponent;

/**
 * Created by dvelasquez on 2/23/17.
 */
@UIScope
@Subcomponent(
        modules = {
                HomeFeaturedModule.class,
        })
public interface EventFeaturedComponent {

    void inject(HomeFeaturedFragment fragment);
}
