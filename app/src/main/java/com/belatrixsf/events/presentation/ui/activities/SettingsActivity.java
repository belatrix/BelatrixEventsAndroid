package com.belatrixsf.events.presentation.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.belatrixsf.events.R;
import com.belatrixsf.events.di.component.UIComponent;
import com.belatrixsf.events.presentation.ui.base.BelatrixBaseActivity;
import com.belatrixsf.events.presentation.ui.fragments.SettingsFragment;
import com.belatrixsf.events.utils.cache.Cache;

import javax.inject.Inject;

import timber.log.Timber;

public class SettingsActivity extends BelatrixBaseActivity  {

    @Inject
    Cache cache;
    Integer currentCity;

    @Override
    protected void initDependencies(UIComponent uiComponent) {
        uiComponent.inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        replaceFragment(SettingsFragment.newInstance(),false);
        setNavigationToolbar();
        currentCity = cache.getCity();
    }

    public static Intent makeIntent(Activity context) {
        Intent intent = new Intent(context, SettingsActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
       // context.overridePendingTransition(0, 0);
        return intent;
    }

    @Override
    public boolean isFinishing() {
        int city1 = currentCity != null ? currentCity.intValue(): -1;
        int city2 = cache.getCity() != null ? cache.getCity().intValue(): -1;
        //reload main activity if user changed the city
        if (city1 != city2){
            startActivity(MainActivity.makeIntentWithoutAnimation(this));
        }
        return super.isFinishing();
    }
}
