package com.belatrix.events.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dvelasquez on 2/24/17.
 * Modified by lburgos
 */

/*
 {
    "id": 32,
    "author": {
      "id": 2,
      "full_name": "Participante Registrado",
      "email": "participante@email.com"
    },
    "title": "Dale Go - App social de eventos deportivos",
    "description": "",
    "event": {
      "id": 19,
      "title": "Hackatrix Lima 2018",
      "image": "http://i.imgur.com/bTHbsu5.jpg"
    },
    "is_completed": false
  }
 */

public class Project implements Parcelable {

    public static final Creator<Project> CREATOR = new Creator<Project>() {
        @Override
        public Project createFromParcel(Parcel source) {
            return new Project(source);
        }

        @Override
        public Project[] newArray(int size) {
            return new Project[size];
        }
    };
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
    @Expose(serialize = false)
    private int mVotes;

    public Project() {

    }


    protected Project(Parcel in) {
        this.mId = in.readInt();
        this.mAuthor = in.readParcelable(Author.class.getClassLoader());
        this.mTitle = in.readString();
        this.mDescription = in.readString();
        this.mEvent = in.readParcelable(Event.class.getClassLoader());
        this.isCompleted = in.readByte() != 0;
        this.mVotes = in.readInt();
    }

    public int getVotes() {
        return mVotes;
    }

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

    public void setVotes(int mVotes) {
        this.mVotes = mVotes;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mId);
        dest.writeParcelable(this.mAuthor, flags);
        dest.writeString(this.mTitle);
        dest.writeString(this.mDescription);
        dest.writeParcelable(this.mEvent, flags);
        dest.writeByte(this.isCompleted ? (byte) 1 : (byte) 0);
        dest.writeInt(this.mVotes);
    }

    static class Event implements Parcelable {
        public static final Creator<Event> CREATOR = new Creator<Event>() {
            @Override
            public Event createFromParcel(Parcel source) {
                return new Event(source);
            }

            @Override
            public Event[] newArray(int size) {
                return new Event[size];
            }
        };
        @SerializedName("id")
        private int mId;
        @SerializedName("title")
        private String mTitle;
        @SerializedName("image")
        private String mImage;

        public Event() {
        }

        protected Event(Parcel in) {
            this.mId = in.readInt();
            this.mTitle = in.readString();
            this.mImage = in.readString();
        }

        public int getId() {
            return mId;
        }

        public String getTitle() {
            return mTitle;
        }

        public String getImage() {
            return mImage;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.mId);
            dest.writeString(this.mTitle);
            dest.writeString(this.mImage);
        }
    }

    static class Author implements Parcelable {

        public static final Parcelable.Creator<Author> CREATOR = new Parcelable.Creator<Author>() {
            @Override
            public Author createFromParcel(Parcel source) {
                return new Author(source);
            }

            @Override
            public Author[] newArray(int size) {
                return new Author[size];
            }
        };
        @SerializedName("id")
        private int mId;
        @SerializedName("full_name")
        private String mFullName;
        @SerializedName("email")
        private String mEmail;

        public Author() {
        }

        protected Author(Parcel in) {
            this.mId = in.readInt();
            this.mFullName = in.readString();
            this.mEmail = in.readString();
        }

        public int getId() {
            return mId;
        }

        public String getFullName() {
            return mFullName;
        }

        public String getEmail() {
            return mEmail;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.mId);
            dest.writeString(this.mFullName);
            dest.writeString(this.mEmail);
        }
    }
}
