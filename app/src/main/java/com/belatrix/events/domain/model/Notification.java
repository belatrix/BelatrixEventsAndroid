package com.belatrix.events.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by raulrashuaman on 4/11/17.
 */

/*
{
    "id": 1,
    "text": "lorem ipsum",
    "datetime": "2017-04-11T21:46:09.582991Z",
    "city": 1,
    "is_active": true
}
 */
public class Notification implements Parcelable {

    private int id;
    private String text;
    @SerializedName("datetime")
    private String dateTime;
    private int city;

    public Notification() {

    }

    protected Notification(Parcel parcel) {
        id = parcel.readInt();
        text = parcel.readString();
        dateTime = parcel.readString();
        city = parcel.readInt();
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getDateTime() {
        return dateTime;
    }

    public int getCity() {
        return city;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(text);
        dest.writeString(dateTime);
        dest.writeInt(city);
    }

    //creator - used when un-parceling our parcel (creating the object)
    public static final Creator<Notification> CREATOR = new Creator<Notification>() {
        @Override
        public Notification createFromParcel(Parcel parcel) {
            return new Notification(parcel);
        }

        @Override
        public Notification[] newArray(int size) {
            return new Notification[size];
        }
    };
}
