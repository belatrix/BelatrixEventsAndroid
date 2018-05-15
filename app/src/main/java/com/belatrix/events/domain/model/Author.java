package com.belatrix.events.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Author implements Parcelable {
    public static final Parcelable.Creator<Author> CREATOR = new Parcelable.Creator<Author>() {
        @Override
        public Author createFromParcel(Parcel source) {
            return new Author(source);
        }

        @Override
        public Author[] newArray(int size) {
            return new Author[size];
        }
    };
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

    public Author() {
    }

    protected Author(Parcel in) {
        this.mId = in.readInt();
        this.mFullName = in.readString();
        this.mEmail = in.readString();
        this.mPhoneNumber = in.readString();
        this.mRole = in.readParcelable(Role.class.getClassLoader());
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mId);
        dest.writeString(this.mFullName);
        dest.writeString(this.mEmail);
        dest.writeString(this.mPhoneNumber);
        dest.writeParcelable(this.mRole, flags);
    }
}
