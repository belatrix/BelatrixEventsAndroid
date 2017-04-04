package com.belatrixsf.events.utils.cache.impl;

import android.content.SharedPreferences;

import com.belatrixsf.events.utils.cache.Cache;

/**
 * Created by dvelasquez on 4/4/17.
 */

public class CachePreferences implements Cache{

    SharedPreferences preferences;

    private static final String PREFIX_EVENT = "_event";

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
}
