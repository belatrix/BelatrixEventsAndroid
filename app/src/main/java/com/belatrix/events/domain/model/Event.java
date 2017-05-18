package com.belatrix.events.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dvelasquez on 2/24/17.
 */

/*
{
  "id": 2,
  "title": "Hackatrix Lima 2017",
  "image": "http://hackatrix.belatrixsf.com/2016_arg/media/bg_compu_codigo.jpg",
  "datetime": "2017-05-20T09:00:00Z",
  "address": "Oficinas BCP",
  "details": "Es la Hackathon anual de Belatrix para promover el desarrollo colectivo de ideas sociales e innovadoras en un ambiente de diversión y adrenalina pura.",
  "register_link": "http://hackatrix.belatrixsf.com/2016_arg/#Registro",
  "sharing_text": "Viví la experiencia de programar en vivo",
  "location": {
    "id": 1,
    "name": "Facultad de Ingenieria y Arquitectura - USMP",
    "latitude": "-12.072049",
    "longitude": "-76.9443491"
  }
  "has_interactions": true,
  "interaction_text": "Según tu opinion, ¿Qué proyecto consideras que debe ganar la Hackatrix?",
  "interaction_confirmation_text": "¿Está seguro que desea votar  por el proyecto ABC?",
  "is_featured": true,
  "is_upcoming": true,
  "is_interaction_active": true,
  "is_active": true
}
 */

public class Event implements Parcelable{

    private int id;
    private String title;
    private String image;
    private String details;
    private String datetime;
    private String address;
    @SerializedName("register_link")
    private String registerLink;
    @SerializedName("sharing_text")
    private String sharingText;
    private Location location;
    @SerializedName("has_interactions")
    private boolean hasInteractions;
    @SerializedName("interaction_text")
    private String interactionText;
    @SerializedName("interaction_confirmation_text")
    private String interactionConfirmationText;
    @SerializedName("is_featured")
    private boolean isFeatured;
    @SerializedName("is_upcoming")
    private boolean isUpcoming;
    @SerializedName("is_interaction_active")
    private boolean isInteractionActive;
    @SerializedName("is_active")
    private boolean isActive;


    public Event(){

    }

    public Event(Parcel parcel){
        id = parcel.readInt();
        title = parcel.readString();
        image = parcel.readString();
        details = parcel.readString();
        datetime = parcel.readString();
        address = parcel.readString();
        registerLink = parcel.readString();
        sharingText = parcel.readString();
        location = (Location) parcel.readValue(Location.class.getClassLoader());
        hasInteractions = parcel.readByte() != 0x00;
        interactionText = parcel.readString();
        interactionConfirmationText = parcel.readString();
        isFeatured = parcel.readByte() != 0x00;
        isUpcoming = parcel.readByte() != 0x00;
        isInteractionActive = parcel.readByte() != 0x00;
        isActive = parcel.readByte() != 0x00;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getRegisterLink() {
        return registerLink;
    }

    public void setRegisterLink(String registerLink) {
        this.registerLink = registerLink;
    }

    public String getSharingText() {
        return sharingText;
    }

    public void setSharingText(String sharingText) {
        this.sharingText = sharingText;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getInteractionText() {
        return interactionText;
    }

    public void setInteractionText(String interactionText) {
        this.interactionText = interactionText;
    }

    public String getInteractionConfirmationText() {
        return interactionConfirmationText;
    }

    public void setInteractionConfirmationText(String interactionConfirmationText) {
        this.interactionConfirmationText = interactionConfirmationText;
    }

    public boolean isFeatured() {
        return isFeatured;
    }

    public void setFeatured(boolean featured) {
        isFeatured = featured;
    }

    public boolean isUpcoming() {
        return isUpcoming;
    }

    public void setUpcoming(boolean upcoming) {
        isUpcoming = upcoming;
    }

    public boolean isInteractionActive() {
        return isInteractionActive;
    }

    public void setInteractionActive(boolean interactionActive) {
        isInteractionActive = interactionActive;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isHasInteractions() {
        return hasInteractions;
    }

    public void setHasInteractions(boolean hasInteractions) {
        this.hasInteractions = hasInteractions;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(image);
        dest.writeString(details);
        dest.writeString(datetime);
        dest.writeString(address);
        dest.writeString(registerLink);
        dest.writeString(sharingText);
        dest.writeValue(location);
        dest.writeByte((byte) (hasInteractions ? 0x01 : 0x00));
        dest.writeString(interactionText);
        dest.writeString(interactionConfirmationText);
        dest.writeByte((byte) (isFeatured ? 0x01 : 0x00));
        dest.writeByte((byte) (isUpcoming ? 0x01 : 0x00));
        dest.writeByte((byte) (isInteractionActive ? 0x01 : 0x00));
        dest.writeByte((byte) (isActive ? 0x01 : 0x00));
    }

    //creator - used when un-parceling our parcle (creating the object)
    public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>(){

        @Override
        public Event createFromParcel(Parcel parcel) {
            return new Event(parcel);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[0];
        }
    };

}
