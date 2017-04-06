package com.belatrixsf.events.utils.cache;

/**
 * Created by dvelasquez on 4/4/17.
 */

public interface Cache {

    void saveVote(int eventId);
    boolean alreadyVoted(int eventId);
    void saveCity(int cityId);
    int getCity();
    void clearFirstTime();
    boolean isFirstTime();
    void updateFirstTime();
}
