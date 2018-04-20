package com.belatrix.events.di.module;

import com.belatrix.events.data.datasource.rest.retrofit.api.DeviceAPI;
import com.belatrix.events.data.datasource.rest.retrofit.api.EmployeeAPI;
import com.belatrix.events.data.datasource.rest.retrofit.api.EventAPI;
import com.belatrix.events.data.datasource.rest.retrofit.api.NotificationAPI;
import com.belatrix.events.data.datasource.rest.retrofit.api.UserAPI;
import com.belatrix.events.data.datasource.rest.retrofit.server.DeviceRepositoryImpl;
import com.belatrix.events.data.datasource.rest.retrofit.server.EmployeeRepositoryImpl;
import com.belatrix.events.data.datasource.rest.retrofit.server.EventRepositoryImpl;
import com.belatrix.events.data.datasource.rest.retrofit.server.NotificationRepositoryImpl;
import com.belatrix.events.data.datasource.rest.retrofit.server.UserRepositoryImpl;
import com.belatrix.events.domain.repository.DeviceRepository;
import com.belatrix.events.domain.repository.EmployeeRepository;
import com.belatrix.events.domain.repository.EventRepository;
import com.belatrix.events.domain.repository.NotificationRepository;
import com.belatrix.events.domain.repository.UserRepository;

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

    @Singleton
    @Provides
    public DeviceRepository provideDeviceRepository(DeviceAPI deviceAPI) {
        return new DeviceRepositoryImpl(deviceAPI);
    }

    @Singleton
    @Provides
    public NotificationRepository provideNotificationRepository(NotificationAPI notificationAPI) {
        return new NotificationRepositoryImpl(notificationAPI);
    }

    @Singleton
    @Provides
    public UserRepository providesUserRepository(UserAPI userAPI) {
        return new UserRepositoryImpl(userAPI);
    }
}
