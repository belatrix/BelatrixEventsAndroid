package com.belatrix.events.presentation.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.belatrix.events.R;
import com.belatrix.events.di.component.UIComponent;
import com.belatrix.events.presentation.ui.base.BelatrixBaseActivity;
import com.belatrix.events.presentation.ui.fragments.SettingsFragment;
import com.belatrix.events.utils.cache.Cache;

import javax.inject.Inject;

public class SettingsActivity extends BelatrixBaseActivity {

    @Inject
    Cache cache;
    Integer currentCity;

    public static Intent makeIntent(Activity context) {
        Intent intent = new Intent(context, SettingsActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        // context.overridePendingTransition(0, 0);
        return intent;
    }

    @Override
    protected void initDependencies(UIComponent uiComponent) {
        uiComponent.inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        replaceFragment(SettingsFragment.newInstance(), false);
        setNavigationToolbar();
        currentCity = cache.getCity();
    }

    @Override
    public boolean isFinishing() {
        int city1 = currentCity != null ? currentCity.intValue() : -1;
        int city2 = cache.getCity() != null ? cache.getCity().intValue() : -1;
        //reload main activity if user changed the city
        if (city1 != city2) {
            startActivity(MainActivity.makeIntentWithoutAnimation(this));
        }
        return super.isFinishing();
    }

}
