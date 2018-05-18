package com.belatrix.events.domain.model;

import com.google.gson.annotations.SerializedName;

/*
{
  "id": 12,
  "email": "lburgos@belatrixsf.com",
  "full_name": "Luis Miguel Burgos",
  "phone_number": "987654321",
  "role": {
    "id": 3,
    "name": "Mobile Developer"
  },
  "is_moderator": false,
  "is_staff": false,
  "is_active": true,
  "is_jury": false,
  "is_password_reset_required": false
}
 */
public class User {

    @SerializedName("id")
    private int mId;

    @SerializedName("email")
    private String mEmail;

    @SerializedName("full_name")
    private String mFullName;

    @SerializedName("role")
    private Role role;

    @SerializedName("is_moderator")
    private boolean isModerator;

    @SerializedName("is_staff")
    private boolean isStaff;

    @SerializedName("is_active")
    private boolean isActive;

    @SerializedName("is_jury")
    private boolean isJury;

    @SerializedName("is_participant")
    private boolean isParticipant;

    public int getId() {
        return mId;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getFullName() {
        return mFullName;
    }

    public Role getRole() {
        return role;
    }

    public boolean isModerator() {
        return isModerator;
    }

    public boolean isStaff() {
        return isStaff;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isJury() {
        return isJury;
    }

    public boolean isParticipant() {
        return isParticipant;
    }
}
