package com.belatrixsf.events.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dvelasquez on 2/24/17.
 */

/*
 {
    "id": 1,
    "name": "Facultad de Ingenieria y Arquitectura - USMP",
    "latitude": "-12.072049",
    "longitude": "-76.9443491"
  }
 */

public class Location implements Parcelable {

    private int id;
    private String name;
    private String latitude;
    private String longitude;

    public Location(Parcel parcel) {
        id = parcel.readInt();
        name = parcel.readString();
        latitude = parcel.readString();
        longitude = parcel.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(latitude);
        dest.writeString(longitude);
    }

    //creator - used when un-parceling our parcle (creating the object)
    public static final Creator<Location> CREATOR = new Creator<Location>() {

        @Override
        public Location createFromParcel(Parcel parcel) {
            return new Location(parcel);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[0];
        }
    };
}
