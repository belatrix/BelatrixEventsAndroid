package com.belatrixsf.events.data.datasource.rest.retrofit.server;

import com.belatrixsf.events.data.datasource.ServerCallBack;
import com.belatrixsf.events.data.datasource.rest.retrofit.api.EventAPI;
import com.belatrixsf.events.data.datasource.rest.retrofit.base.BaseRepository;
import com.belatrixsf.events.domain.repository.EventRepository;

import java.util.List;

import retrofit2.Call;

/**
 * Created by diegoveloper on 3/31/17.
 */

public class EventRepositoryImpl extends BaseRepository<List<Contributor>> implements EventRepository {

    EventAPI eventAPI;

    public EventRepositoryImpl(EventAPI eventAPI) {
        this.eventAPI = eventAPI;
    }

    @Override
    public void getHomeEvent(ServerCallBack<List<Contributor>> callBack) {
        Call<List<Contributor>> call = eventAPI.repoContributors("square", "retrofit");
        executeRequest(callBack, call);
    }

}
