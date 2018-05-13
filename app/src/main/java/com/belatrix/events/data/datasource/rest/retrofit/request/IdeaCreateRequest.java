package com.belatrix.events.data.datasource.rest.retrofit.request;

import com.google.gson.annotations.SerializedName;

public class IdeaCreateRequest {
    
    @SerializedName("author")
    private int mAuthor;
    
    @SerializedName("event")
    private int mEvent;
    
    @SerializedName("title")
    private String mTitle;
    
    @SerializedName("description")
    private String mDescription;

    public void setAuthor(int mAuthor) {
        this.mAuthor = mAuthor;
    }

    public void setEvent(int mEvent) {
        this.mEvent = mEvent;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }
}
