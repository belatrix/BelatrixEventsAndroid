package com.belatrix.events.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dvelasquez on 2/24/17.
 */
/*
 {
    "id": 1,
    "name": "Lima"
  }
 */

public class City implements Parcelable {

    private int id;
    private String name;

    public City(){

    }

    public City(Parcel parcel){
        id = parcel.readInt();
        name = parcel.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeInt(id);
        dest.writeString(name);
    }

    //creator - used when un-parceling our parcle (creating the object)
    public static final Creator<City> CREATOR = new Creator<City>(){

        @Override
        public City createFromParcel(Parcel parcel) {
            return new City(parcel);
        }

        @Override
        public City[] newArray(int size) {
            return new City[0];
        }
    };
}
