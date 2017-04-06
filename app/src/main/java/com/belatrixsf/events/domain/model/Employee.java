package com.belatrixsf.events.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dvelasquez on 4/6/17.
 */

/*
{
  "id": 1,
  "name": "Diego Velasquez",
  "avatar": "https://pbs.twimg.com/profile_images/1144458057/avatar.jpg",
  "email": "dvelasquez@belatrixsf.com",
  "role": "Android Developer",
  "twitter": "@diegoveloper",
  "github": "@diegoveloper",
  "website": "https://about.me/diegoveloper"
}
 */

public class Employee implements Parcelable {

    private int id;
    private String name;
    private String avatar;
    private String email;
    private String role;
    private String twitter;
    private String github;
    private String website;

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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getGithub() {
        return github;
    }

    public void setGithub(String github) {
        this.github = github;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    protected Employee(Parcel parcel) {
        id = parcel.readInt();
        name = parcel.readString();
        avatar = parcel.readString();
        email = parcel.readString();
        role = parcel.readString();
        twitter = parcel.readString();
        github = parcel.readString();
        website = parcel.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(avatar);
        dest.writeString(email);
        dest.writeString(role);
        dest.writeString(twitter);
        dest.writeString(github);
        dest.writeString(website);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Employee> CREATOR = new Creator<Employee>() {
        @Override
        public Employee createFromParcel(Parcel in) {
            return new Employee(in);
        }

        @Override
        public Employee[] newArray(int size) {
            return new Employee[size];
        }
    };
}