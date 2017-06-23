package com.belatrix.events;

import com.belatrix.events.di.component.ApplicationComponent;
import com.belatrix.events.di.module.ApplicationModule;
import com.belatrix.events.domain.executor.MainThread;

import it.cosenonjaviste.daggermock.DaggerMockRule;

public class JUnitDaggerMockRule extends DaggerMockRule<ApplicationComponent> {
    public JUnitDaggerMockRule() {
        super(ApplicationComponent.class, new ThreadModule(), new ApplicationModule(null));
        providesMock(MainThread.class);
       // provides(UIModule.class, LoginModuleTest.class);
    }
}