package com.belatrixsf.events.di.component;

import android.content.Context;

import com.belatrixsf.events.BxEventsApplication;
import com.belatrixsf.events.di.module.ApplicationModule;
import com.belatrixsf.events.di.module.RepositoryModule;
import com.belatrixsf.events.di.module.RetrofitModule;
import com.belatrixsf.events.di.module.ThreadModule;
import com.belatrixsf.events.domain.executor.Executor;
import com.belatrixsf.events.domain.executor.MainThread;
import com.belatrixsf.events.domain.repository.EmployeeRepository;
import com.belatrixsf.events.domain.repository.EventRepository;
import com.belatrixsf.events.domain.repository.Repository;
import com.belatrixsf.events.utils.cache.Cache;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by dvelasquez on 2/23/17.
 */

@Singleton
@Component(modules = {ApplicationModule.class,
        ThreadModule.class, RetrofitModule.class, RepositoryModule.class

})
public interface ApplicationComponent {

    @Singleton
    Context context();
    void inject(BxEventsApplication bxEventsApplication);
    MainThread mainThread();
    Executor executor();
    Repository repository();
    EventRepository eventRepository();
    EmployeeRepository employeeRepository();
    Cache provideCache();
}
