package com.belatrixsf.events.di.component;

import com.belatrixsf.events.di.module.UIModule;
import com.belatrixsf.events.di.scope.UIScope;
import com.belatrixsf.events.presentation.ui.activities.LoginActivity;
import com.belatrixsf.events.presentation.ui.activities.MainActivity;
import com.belatrixsf.events.presentation.ui.fragments.EventDetailVoteFragment;
import com.belatrixsf.events.presentation.ui.fragments.HomeFeaturedFragment;
import com.belatrixsf.events.presentation.ui.fragments.HomeFragment;
import com.belatrixsf.events.presentation.ui.fragments.LoginFragment;

import dagger.Component;

/**
 * Created by dvelasquez on 2/23/17.
 */
@UIScope
@Component(
        dependencies = ApplicationComponent.class,
        modules = UIModule.class)
public interface UIComponent {
    void inject(LoginActivity activity);
    void inject(MainActivity activity);
    void inject(LoginFragment fragment);
    void inject(HomeFragment fragment);
    void inject(HomeFeaturedFragment fragment);
    void inject(EventDetailVoteFragment fragment);
}
