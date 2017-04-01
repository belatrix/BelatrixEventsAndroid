package com.belatrixsf.events.di.module;

import com.belatrixsf.events.data.datasource.rest.retrofit.api.EventAPI;
import com.belatrixsf.events.data.datasource.rest.retrofit.server.EventRepositoryImpl;
import com.belatrixsf.events.domain.repository.EventRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
/**
 * Created by diegoveloper on 3/31/17.
 */

@Module
public class RepositoryModule {

    @Singleton
    @Provides
    public EventRepository provideEventRepository(EventAPI eventAPI) {
        return new EventRepositoryImpl(eventAPI);
    }
}
