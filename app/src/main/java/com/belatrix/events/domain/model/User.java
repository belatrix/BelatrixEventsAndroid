package com.belatrix.events.domain.model;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("email")
    private String mEmail;

    @SerializedName("first_name")
    private String mFirstName;

    @SerializedName("last_name")
    private String mLastName;

    @SerializedName("is_staff")
    private boolean isStaff;

    @SerializedName("is_active")
    private boolean isActive;

    @SerializedName("is_participant")
    private boolean isParticipant;

    public String getEmail() {
        return mEmail;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public boolean isStaff() {
        return isStaff;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isParticipant() {
        return isParticipant;
    }
}
