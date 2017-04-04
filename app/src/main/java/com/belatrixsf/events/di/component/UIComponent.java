package com.belatrixsf.events.di.component;

import com.belatrixsf.events.di.module.UIModule;
import com.belatrixsf.events.di.scope.UIScope;
import com.belatrixsf.events.presentation.ui.activities.MainActivity;
import com.belatrixsf.events.presentation.ui.fragments.AboutFragment;
import com.belatrixsf.events.presentation.ui.fragments.EventDetailVoteFragment;
import com.belatrixsf.events.presentation.ui.fragments.EventListFragment;
import com.belatrixsf.events.presentation.ui.fragments.EventListSummaryFragment;
import com.belatrixsf.events.presentation.ui.fragments.FinderFragment;
import com.belatrixsf.events.presentation.ui.fragments.HomeFragment;

import dagger.Component;

/**
 * Created by dvelasquez on 2/23/17.
 */
@UIScope
@Component(
        dependencies = ApplicationComponent.class,
        modules = UIModule.class)
public interface UIComponent {
    void inject(MainActivity activity);
    void inject(HomeFragment fragment);
    void inject(EventDetailVoteFragment fragment);
    void inject(EventListSummaryFragment fragment);
    void inject(EventListFragment fragment);
    void inject(AboutFragment fragment);
    void inject(FinderFragment fragment);
}
