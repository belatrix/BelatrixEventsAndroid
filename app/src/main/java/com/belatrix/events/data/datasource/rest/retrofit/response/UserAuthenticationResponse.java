package com.belatrix.events.data.datasource.rest.retrofit.response;

import com.google.gson.annotations.SerializedName;

public class UserAuthenticationResponse {

    @SerializedName("token")
    private String mToken;

    @SerializedName("user_id")
    private int mUserId;

    @SerializedName("email")
    private String mEmail;

    @SerializedName("is_staff")
    private boolean isStaff;

    @SerializedName("is_participant")
    private boolean isParticipant;

    @SerializedName("isJury")
    private boolean isJury;

    @SerializedName("is_password_reset_required")
    private boolean isPasswordResetRequired;

    public String getToken() {
        return mToken;
    }

    public int getUserId() {
        return mUserId;
    }

    public String getEmail() {
        return mEmail;
    }

    public boolean isStaff() {
        return isStaff;
    }

    public boolean isParticipant() {
        return isParticipant;
    }

    public boolean isJury() {
        return isJury;
    }

    public boolean isPasswordResetRequired() {
        return isPasswordResetRequired;
    }
}
