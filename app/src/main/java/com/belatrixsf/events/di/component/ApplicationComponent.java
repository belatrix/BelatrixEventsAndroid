package com.belatrixsf.events.di.component;

import com.belatrixsf.events.di.module.ApplicationModule;
import com.belatrixsf.events.di.module.EventDetailVoteModule;
import com.belatrixsf.events.di.module.HomeFeaturedModule;
import com.belatrixsf.events.di.module.HomeModule;
import com.belatrixsf.events.di.module.LoginModule;
import com.belatrixsf.events.di.module.ThreadModule;

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

    LoginComponent loadModule(LoginModule module);
    HomeComponent loadModule(HomeModule module);
    EventFeaturedComponent loadModule(HomeFeaturedModule module);
    EventDetailVoteComponent loadModule(EventDetailVoteModule module);
}
