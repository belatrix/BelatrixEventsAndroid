package com.belatrixsf.events.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by dvelasquez on 2/24/17.
 */

/*
 {
    "id": 3,
    "text": "Sabueso de mascotas perdidas",
    "votes": 0,
    "is_active": true,
    "event": 2
  },
 */

public class Project implements Comparable<Project>,Parcelable {

    private int id;
    private String text;
    private int votes;
    private transient boolean highest;

    public Project(Parcel parcel){
        id = parcel.readInt();
        text = parcel.readString();
        votes = parcel.readInt();
    }

    public boolean isHighest() {
        return highest;
    }

    public void setHighest(boolean highest) {
        this.highest = highest;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    //TODO: Remove this
    public static List<Project> getDummyData(){
        Random random = new Random();

        List<Project> projectList = new ArrayList<>();/*
        for(int i = 1; i < 21 ; i++){
            projectList.add(new Project(i,"Proyecto "+ i ,getRandomInt(random,1,250)));
        }
        */
        return projectList;
    }

    public static int getRandomInt(Random random, int min, int max)
    {
        return random.nextInt(max - min + 1) + min;
    }


    @Override
    public int compareTo(Project project) {
        return project.getVotes() - getVotes();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeInt(id);
        dest.writeString(text);
        dest.writeInt(votes);
    }

    //creator - used when un-parceling our parcle (creating the object)
    public static final Parcelable.Creator<Project> CREATOR = new Parcelable.Creator<Project>(){

        @Override
        public Project createFromParcel(Parcel parcel) {
            return new Project(parcel);
        }

        @Override
        public Project[] newArray(int size) {
            return new Project[0];
        }
    };
}
