package com.belatrix.events.domain.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Profile {

    @SerializedName("author")
    private List<Author> mAuthor;

    @SerializedName("user")
    private User mUser;

    @SerializedName("candidate")
    private List<CandidateList> lstCandidate;

    @SerializedName("participant")
    private List<ParticipantList> lstParticipant;

    @SerializedName("attendance")
    private List<AttendanceList> lstAttendance;

    @SerializedName("events")
    private List<EventList> lstEvents;

    public List<Author> getAuthor() {
        return mAuthor;
    }

    public User getmser() {
        return mUser;
    }

    public List<CandidateList> getLstCandidate() {
        return lstCandidate;
    }

    public List<ParticipantList> getLstParticipant() {
        return lstParticipant;
    }

    public List<AttendanceList> getLstAttendance() {
        return lstAttendance;
    }

    public List<EventList> getLstEvents() {
        return lstEvents;
    }

    public class CandidateList {
        @SerializedName("idea")
        private Idea mIdea;

        public Idea getIdea() {
            return mIdea;
        }
    }

    public class ParticipantList {
        @SerializedName("idea")
        private Idea mIdea;

        public Idea getIdea() {
            return mIdea;
        }
    }

    public class AttendanceList {
        @SerializedName("meeting")
        private Meeting mMeeting;

        public Meeting getMeeting() {
            return mMeeting;
        }
    }

    public class EventList {
        @SerializedName("event")
        private Event mEvent;

        public Event getEvent() {
            return mEvent;
        }
    }

    public class Idea {
        @SerializedName("id")
        private int id;

        @SerializedName("title")
        private String title;

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }
    }

    public class Event {
        @SerializedName("id")
        private int id;

        @SerializedName("title")
        private String title;

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }
    }

    public class Meeting {
        @SerializedName("id")
        private int id;

        @SerializedName("name")
        private String name;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}
