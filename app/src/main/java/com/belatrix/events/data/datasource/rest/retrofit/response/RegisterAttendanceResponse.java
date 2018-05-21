package com.belatrix.events.data.datasource.rest.retrofit.response;

import com.belatrix.events.domain.model.User;
import com.google.gson.annotations.SerializedName;

public class RegisterAttendanceResponse {

    @SerializedName("user")
    private User mUser;

    public User getUser() {
        return mUser;
    }
}
