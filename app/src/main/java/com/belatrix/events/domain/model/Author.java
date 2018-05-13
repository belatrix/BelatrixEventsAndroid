package com.belatrix.events.domain.model;

import com.google.gson.annotations.SerializedName;

public class Author {
    @SerializedName("id")
    private int mId;

    @SerializedName("full_name")
    private String mFullName;

    @SerializedName("email")
    private String mEmail;

    @SerializedName("phone_number")
    private String mPhoneNumber;

    @SerializedName("role")
    private Role mRole;

    public int getId() {
        return mId;
    }

    public String getFullName() {
        return mFullName;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public Role getRole() {
        return mRole;
    }
}
