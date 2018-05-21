package com.belatrix.events.data.datasource.rest.retrofit.response;

import com.belatrix.events.domain.model.Author;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CandidatesResponse {

    @SerializedName("is_candidate")
    private boolean isCandidate;

    @SerializedName("candidates")
    private List<TeamMembers> lstCandidates;

    public boolean isCandidate() {
        return isCandidate;
    }

    public List<TeamMembers> getLstCandidates() {
        return lstCandidates;
    }

    public class TeamMembers {
        @SerializedName("user")
        private Author mAuthor;

        public Author getAuthor() {
            return mAuthor;
        }
    }
}
