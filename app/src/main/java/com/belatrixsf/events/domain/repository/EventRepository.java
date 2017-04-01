package com.belatrixsf.events.domain.repository;

import com.belatrixsf.events.data.datasource.ServerCallBack;
import com.belatrixsf.events.data.datasource.rest.retrofit.server.Contributor;

import java.util.List;

/**
 * Created by diegoveloper on 3/31/17.
 */

public interface EventRepository {

    void getHomeEvent(ServerCallBack<List<Contributor>> callBack);
}
