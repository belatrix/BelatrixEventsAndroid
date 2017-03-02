package com.belatrixsf.events.di.component;

import android.content.Context;

import com.belatrixsf.events.BxEventsApplication;
import com.belatrixsf.events.di.module.ApplicationModule;
import com.belatrixsf.events.di.module.ThreadModule;
import com.belatrixsf.events.domain.executor.Executor;
import com.belatrixsf.events.domain.executor.MainThread;
import com.belatrixsf.events.domain.repository.Repository;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by dvelasquez on 2/23/17.
 */

@Singleton
@Component(modules = {ApplicationModule.class,
        ThreadModule.class

})
public interface ApplicationComponent {

    @Singleton
    Context context();
    //BxEventsApplication application();
    void inject(BxEventsApplication bxEventsApplication);
    MainThread mainThread();
    Executor executor();
    Repository repository();
}
