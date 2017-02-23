package com.belatrixsf.events.di.component;

import com.belatrixsf.events.di.module.ContextModule;
import com.belatrixsf.events.di.module.ThreadModule;
import com.belatrixsf.events.di.scope.ApplicationScope;

import dagger.Component;

/**
 * Created by dvelasquez on 2/23/17.
 */
@ApplicationScope
@Component(modules = { ThreadModule.class, ContextModule.class})
public interface ApplicationComponent {
}
