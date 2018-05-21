package com.belatrix.events.domain.model;

import com.google.gson.annotations.SerializedName;

/*
{
    "id": 2,
    "name": "Full Day - Hackatrix 2018",
    "start_date": "2018-05-26T14:00:00Z",
    "end_date": "2018-05-27T01:00:00Z",
    "is_active": true,
    "is_over": false,
    "event": 19
  }
 */
public class Meeting {
    @SerializedName("id")
    private int mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("start_date")
    private String mStartDate;

    @SerializedName("end_date")
    private String mEndDate;

    @SerializedName("is_active")
    private boolean isActive;

    @SerializedName("is_over")
    private boolean isOver;

    @SerializedName("event")
    private int eventId;

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getStartDate() {
        return mStartDate;
    }

    public String getEndDate() {
        return mEndDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isOver() {
        return isOver;
    }

    public int getEventId() {
        return eventId;
    }
}