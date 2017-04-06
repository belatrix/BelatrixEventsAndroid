package com.belatrixsf.events.utils.cache.impl;

import android.content.SharedPreferences;

import com.belatrixsf.events.utils.cache.Cache;

/**
 * Created by dvelasquez on 4/4/17.
 */

public class CachePreferences implements Cache{

    SharedPreferences preferences;

    private static final String PREFIX_EVENT = "_event";
    private static final String PARAM_CITY = "_city_";
    private static final String PARAM_FIRST_TIME = "_firstTime_";

    public CachePreferences(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    @Override
    public void saveVote(int eventId) {
        preferences.edit().putBoolean(PREFIX_EVENT + eventId, true).commit();
    }

    @Override
    public boolean alreadyVoted(int eventId) {
        return preferences.getBoolean(PREFIX_EVENT + eventId, false);
    }

    @Override
    public void saveCity(int cityId) {
        preferences.edit().putInt(PARAM_CITY,cityId).commit();
    }

    @Override
    public int getCity() {
        return preferences.getInt(PARAM_CITY,1);
    }

    @Override
    public void clearFirstTime() {
        preferences.edit().putBoolean(PARAM_FIRST_TIME,true).commit();
    }

    @Override
    public void updateFirstTime() {
        preferences.edit().putBoolean(PARAM_FIRST_TIME,false).commit();
    }

    @Override
    public boolean isFirstTime() {
        return preferences.getBoolean(PARAM_FIRST_TIME,true);
    }
}
