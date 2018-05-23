package com.belatrix.events.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

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
public class User implements Parcelable {

    @SerializedName("id")
    private int mId;

    @SerializedName("email")
    private String mEmail;

    @SerializedName("full_name")
    private String mFullName;

    @SerializedName("phone_number")
    private String mPhoneNumber;

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

    public String getPhoneNumber() {
        return mPhoneNumber;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mId);
        dest.writeString(this.mEmail);
        dest.writeString(this.mFullName);
        dest.writeString(this.mPhoneNumber);
        dest.writeParcelable(this.role, flags);
        dest.writeByte(this.isModerator ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isStaff ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isActive ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isJury ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isParticipant ? (byte) 1 : (byte) 0);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.mId = in.readInt();
        this.mEmail = in.readString();
        this.mFullName = in.readString();
        this.mPhoneNumber = in.readString();
        this.role = in.readParcelable(Role.class.getClassLoader());
        this.isModerator = in.readByte() != 0;
        this.isStaff = in.readByte() != 0;
        this.isActive = in.readByte() != 0;
        this.isJury = in.readByte() != 0;
        this.isParticipant = in.readByte() != 0;
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
