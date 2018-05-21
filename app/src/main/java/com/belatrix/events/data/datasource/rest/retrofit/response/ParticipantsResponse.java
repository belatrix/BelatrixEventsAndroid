package com.belatrix.events.data.datasource.rest.retrofit.response;

import com.belatrix.events.domain.model.Author;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ParticipantsResponse {

    @SerializedName("is_registered")
    private boolean isRegistered;

    @SerializedName("team_members")
    private List<TeamMembers> lstTeamMembers;

    public boolean isRegistered() {
        return isRegistered;
    }

    public List<TeamMembers> getLstTeamMembers() {
        return lstTeamMembers;
    }

    public class TeamMembers {
        @SerializedName("user")
        private Author mAuthor;

        public Author getAuthor() {
            return mAuthor;
        }
    }
}
