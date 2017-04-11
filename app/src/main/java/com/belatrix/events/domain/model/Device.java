package com.belatrix.events.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dvelasquez on 2/24/17.
 */
/*
 {
    "id": 1,
    "device_code": "Lima"
  }
 */

public class Device implements Parcelable {

    private int id;
    @SerializedName("device_code")
    private String deviceCode;

    public Device(){

    }

    public Device(Parcel parcel){
        id = parcel.readInt();
        deviceCode = parcel.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeInt(id);
        dest.writeString(deviceCode);
    }

    //creator - used when un-parceling our parcle (creating the object)
    public static final Creator<Device> CREATOR = new Creator<Device>(){

        @Override
        public Device createFromParcel(Parcel parcel) {
            return new Device(parcel);
        }

        @Override
        public Device[] newArray(int size) {
            return new Device[0];
        }
    };
}
