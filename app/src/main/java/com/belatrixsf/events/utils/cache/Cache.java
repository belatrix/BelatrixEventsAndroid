package com.belatrixsf.events.utils.cache;

/**
 * Created by dvelasquez on 4/4/17.
 */

public interface Cache {

    void saveVote(int eventId);

    boolean alreadyVoted(int eventId);

    void saveCity(Integer cityId);

    void removeCity();

    Integer getCity();

    void clearStartAppFlag();

    boolean isFirstTimeStartApp();

    void updateStartAppFlag();

    void updateFirstTime();

    boolean isFirstTime();

}
