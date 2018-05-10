package com.belatrix.events.domain.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lburgos
 */
/*
{
    "id": 7,
    "title": "Maiden Assistant - App que ofrece recomendaciones basados en la experiencia de la persona",
    "votes": 0
  }
 */
public class Vote {

    @SerializedName("id")
    private int mId;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("votes")
    private int mVotes;

    public int getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public int getVotes() {
        return mVotes;
    }
}
