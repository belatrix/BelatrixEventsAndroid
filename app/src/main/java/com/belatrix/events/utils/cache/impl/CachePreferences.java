package com.belatrix.events.utils.cache.impl;

import android.content.SharedPreferences;

import com.belatrix.events.utils.cache.Cache;

/**
 * Created by dvelasquez on 4/4/17.
 */

public class CachePreferences implements Cache {

    SharedPreferences preferences;

    private static final String PREFIX_EVENT = "_event";
    private static final String PARAM_CITY = "_city_";
    private static final String PARAM_START_APP = "_start_app_";
    private static final String PARAM_FIRST_TIME = "_firstTime_";
    private static final String PARAM_NOTIFICATION = "_notification_";
    private static final String PARAM_DEVICE_TOKEN = "_token_";

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
    public void saveCity(Integer cityId) {
        preferences.edit().putInt(PARAM_CITY, cityId).commit();
    }

    @Override
    public void removeCity() {
        preferences.edit().remove(PARAM_CITY).commit();
    }

    @Override
    public Integer getCity() {
        int city = preferences.getInt(PARAM_CITY, 0);
        if (city != 0) {
            return city;
        } else
            return null;
    }

    @Override
    public void clearStartAppFlag() {
        preferences.edit().putBoolean(PARAM_START_APP, true).commit();
    }

    @Override
    public void updateStartAppFlag() {
        preferences.edit().putBoolean(PARAM_START_APP, false).commit();
    }

    @Override
    public boolean isFirstTimeStartApp() {
        return preferences.getBoolean(PARAM_START_APP, true);
    }


    @Override
    public void updateFirstTime() {
        preferences.edit().putBoolean(PARAM_FIRST_TIME, false).commit();
    }

    @Override
    public boolean isFirstTime() {
        return preferences.getBoolean(PARAM_FIRST_TIME, true);
    }

    @Override
    public boolean isNotificationEnabled() {
        return preferences.getBoolean(PARAM_NOTIFICATION, true);
    }

    @Override
    public void saveNotification(boolean value) {
        preferences.edit().putBoolean(PARAM_NOTIFICATION, value).commit();
    }

    @Override
    public String getDeviceToken() {
        return preferences.getString(PARAM_DEVICE_TOKEN, null);
    }

    @Override
    public void saveDeviceToken(String value) {
        preferences.edit().putString(PARAM_DEVICE_TOKEN, value).commit();
    }

}
