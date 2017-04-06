package com.belatrixsf.events.di.module;

import com.belatrixsf.events.data.datasource.rest.retrofit.api.EmployeeAPI;
import com.belatrixsf.events.data.datasource.rest.retrofit.api.EventAPI;
import com.belatrixsf.events.data.datasource.rest.retrofit.server.EmployeeRepositoryImpl;
import com.belatrixsf.events.data.datasource.rest.retrofit.server.EventRepositoryImpl;
import com.belatrixsf.events.domain.repository.EmployeeRepository;
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

    @Singleton
    @Provides
    public EmployeeRepository provideEmployeeRepository(EmployeeAPI employeeAPI) {
        return new EmployeeRepositoryImpl(employeeAPI);
    }
}
