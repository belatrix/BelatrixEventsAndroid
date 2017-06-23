package com.belatrix.events.di.component;

import android.content.Context;

import com.belatrix.events.BxEventsApplication;
import com.belatrix.events.di.module.ApplicationModule;
import com.belatrix.events.di.module.RepositoryModule;
import com.belatrix.events.di.module.RetrofitModule;
import com.belatrix.events.domain.repository.DeviceRepository;
import com.belatrix.events.domain.repository.EmployeeRepository;
import com.belatrix.events.domain.repository.EventRepository;
import com.belatrix.events.domain.repository.NotificationRepository;
import com.belatrix.events.domain.repository.Repository;
import com.belatrix.events.utils.cache.Cache;
import com.belatrix.events.utils.fcm.EventsFirebaseInstanceIDService;
import com.belatrix.events.utils.fcm.EventsFirebaseMessagingService;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by dvelasquez on 2/23/17.
 */

@Singleton
@Component(modules = {ApplicationModule.class, RetrofitModule.class, RepositoryModule.class

})
public interface ApplicationComponent {

    @Singleton
    Context context();

    void inject(BxEventsApplication bxEventsApplication);

    void inject(EventsFirebaseInstanceIDService eventsFirebaseInstanceIDService);

    void inject(EventsFirebaseMessagingService eventsFirebaseMessagingService);

    Repository repository();

    EventRepository eventRepository();

    EmployeeRepository employeeRepository();

    DeviceRepository deviceRepository();

    NotificationRepository notificationRepository();

    Cache provideCache();
}
