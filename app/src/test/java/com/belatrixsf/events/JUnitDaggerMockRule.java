package com.belatrixsf.events;

import android.os.Looper;

import com.belatrixsf.events.di.component.ApplicationComponent;
import com.belatrixsf.events.di.module.ApplicationModule;
import com.belatrixsf.events.di.module.ThreadModule;
import com.belatrixsf.events.domain.executor.MainThread;

import it.cosenonjaviste.daggermock.DaggerMockRule;

public class JUnitDaggerMockRule extends DaggerMockRule<ApplicationComponent> {
    public JUnitDaggerMockRule() {
        super(ApplicationComponent.class, new ThreadModule(), new ApplicationModule(null));
        providesMock(MainThread.class);
       // provides(LoginModule.class, LoginModuleTest.class);
    }
}