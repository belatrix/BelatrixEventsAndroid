package com.belatrix.events.utils.cache.impl;

import android.content.SharedPreferences;

import com.belatrix.events.utils.cache.Cache;

import java.util.Map;

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
    private static final String PARAM_DEVICE_ID = "_device_id_";
    private static final String PARAM_VERSION_CODE = "VERSION_CODE";

    public CachePreferences(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    @Override
    public void saveVote(int eventId) {
        preferences.edit().putBoolean(PREFIX_EVENT + eventId, true).apply();
    }

    @Override
    public boolean alreadyVoted(int eventId) {
        return preferences.getBoolean(PREFIX_EVENT + eventId, false);
    }

    @Override
    public void saveCity(Integer cityId) {
        preferences.edit().putInt(PARAM_CITY, cityId).apply();
    }

    @Override
    public void removeCity() {
        preferences.edit().remove(PARAM_CITY).apply();
    }

    @Override
    public Integer getCity() {
        int city = preferences.getInt(PARAM_CITY, 0);
        if (city != 0) {
            return city;
        } else
            return -1;
    }

    @Override
    public void clearStartAppFlag() {
        preferences.edit().putBoolean(PARAM_START_APP, true).apply();
    }

    @Override
    public void updateStartAppFlag() {
        preferences.edit().putBoolean(PARAM_START_APP, false).apply();
    }

    @Override
    public boolean isFirstTimeStartApp() {
        return preferences.getBoolean(PARAM_START_APP, true);
    }


    @Override
    public void updateFirstTime() {
        preferences.edit().putBoolean(PARAM_FIRST_TIME, false).apply();
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
        preferences.edit().putBoolean(PARAM_NOTIFICATION, value).apply();
    }

    @Override
    public String getDeviceToken() {
        return preferences.getString(PARAM_DEVICE_TOKEN, null);
    }

    @Override
    public void saveDeviceToken(String value) {
        preferences.edit().putString(PARAM_DEVICE_TOKEN, value).apply();
    }

    @Override
    public Integer getDeviceId() {
        int value =  preferences.getInt(PARAM_DEVICE_ID,0);
        if (value != 0) {
            return value;
        } else
            return null;
    }

    @Override
    public void saveVersionCode(int versionCode) {
        preferences.edit().putInt(PARAM_VERSION_CODE, versionCode).apply();
    }

    @Override
    public Integer getVersionCode() {
        return preferences.getInt(PARAM_VERSION_CODE, 0);
    }

    @Override
    public void deleteVotes() {
        //todo: create a separate vote preferences file
        Map<String, ?> all= preferences.getAll();
        for (Map.Entry<String, ?> entry : all.entrySet())
        {
            String voteKey= entry.getKey();
            if(voteKey.startsWith(PREFIX_EVENT)){
                preferences.edit().putBoolean(voteKey, false).apply();
            }
        }
    }

    @Override
    public void saveDeviceId(Integer value) {
        preferences.edit().putInt(PARAM_DEVICE_ID, value).apply();
    }

}
