package com.belatrix.events.data.datasource.rest.retrofit;

import com.google.gson.annotations.SerializedName;

public class UserChangePasswordRequest {
    
    @SerializedName("current_password")
    private String mCurrentPassword;
    
    @SerializedName("new_password")
    private String mNewPassword;

    public void setCurrentPassword(String currentPassword) {
        this.mCurrentPassword = currentPassword;
    }

    public void setNewPassword(String newPassword) {
        this.mNewPassword = newPassword;
    }
}
