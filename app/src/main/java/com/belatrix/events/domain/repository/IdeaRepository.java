package com.belatrix.events.domain.repository;

import com.belatrix.events.data.datasource.rest.retrofit.response.IdeaCreateResponse;

import io.reactivex.Observable;

public interface IdeaRepository {

    Observable<IdeaCreateResponse> createIdea(String token, int authorId, int eventId, String title, String description);
}
