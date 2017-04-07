package com.belatrixsf.events.di.component;

import com.belatrixsf.events.di.module.UIModule;
import com.belatrixsf.events.di.scope.UIScope;
import com.belatrixsf.events.presentation.ui.activities.AboutActivity;
import com.belatrixsf.events.presentation.ui.activities.CitySelectionActivity;
import com.belatrixsf.events.presentation.ui.activities.EventDetailActivity;
import com.belatrixsf.events.presentation.ui.activities.EventListActivity;
import com.belatrixsf.events.presentation.ui.activities.FinderActivity;
import com.belatrixsf.events.presentation.ui.activities.MainActivity;
import com.belatrixsf.events.presentation.ui.activities.SplashActivity;
import com.belatrixsf.events.presentation.ui.fragments.AboutFragment;
import com.belatrixsf.events.presentation.ui.fragments.CitySelectionFragment;
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
    void inject(SplashActivity activity);
    void inject(MainActivity activity);
    void inject(EventDetailActivity activity);
    void inject(FinderActivity activity);
    void inject(AboutActivity activity);
    void inject(EventListActivity activity);
    void inject(CitySelectionActivity activity);
    void inject(HomeFragment fragment);
    void inject(EventDetailVoteFragment fragment);
    void inject(EventListSummaryFragment fragment);
    void inject(EventListFragment fragment);
    void inject(AboutFragment fragment);
    void inject(FinderFragment fragment);
    void inject(CitySelectionFragment fragment);
}
