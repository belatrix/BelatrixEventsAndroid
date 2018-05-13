package com.belatrix.events.domain.model;

import com.google.gson.annotations.SerializedName;

public class Role {

    @SerializedName("id")
    private int mId;

    @SerializedName("name")
    private String mName;

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }
}
