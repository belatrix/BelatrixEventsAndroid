package com.belatrix.events.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Role implements Parcelable {

    public static final Parcelable.Creator<Role> CREATOR = new Parcelable.Creator<Role>() {
        @Override
        public Role createFromParcel(Parcel source) {
            return new Role(source);
        }

        @Override
        public Role[] newArray(int size) {
            return new Role[size];
        }
    };
    @SerializedName("id")
    private int mId;
    @SerializedName("name")
    private String mName;

    public Role() {
    }


    protected Role(Parcel in) {
        this.mId = in.readInt();
        this.mName = in.readString();
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mId);
        dest.writeString(this.mName);
    }
}
