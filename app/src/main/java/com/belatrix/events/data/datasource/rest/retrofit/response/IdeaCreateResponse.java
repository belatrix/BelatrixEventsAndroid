package com.belatrix.events.data.datasource.rest.retrofit.response;

import com.belatrix.events.domain.model.Author;
import com.belatrix.events.domain.model.Event;
import com.google.gson.annotations.SerializedName;

/*
{
  "id": 0,
  "author": {
    "id": 0,
    "full_name": "",
    "email": "",
    "phone_number": "",
    "role": {
      "id": 0,
      "name": ""
    }
  },
  "title": "",
  "description": "",
  "event": {
    "id": 0,
    "title": "",
    "image": ""
  },
  "is_completed": false,
  "is_valid": false
}
 */
public class IdeaCreateResponse {

    @SerializedName("id")
    private int mId;

    @SerializedName("author")
    private Author mAuthor;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("description")
    private String mDescription;

    @SerializedName("event")
    private Event mEvent;

    @SerializedName("is_completed")
    private boolean isCompleted;

    @SerializedName("is_valid")
    private boolean isValid;

    public int getId() {
        return mId;
    }

    public Author getAuthor() {
        return mAuthor;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public Event getEvent() {
        return mEvent;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public boolean isValid() {
        return isValid;
    }
}
