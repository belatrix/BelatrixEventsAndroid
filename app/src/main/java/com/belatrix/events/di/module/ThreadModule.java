package com.belatrix.events.di.module;

import com.belatrix.events.domain.executor.Executor;
import com.belatrix.events.domain.executor.MainThread;
import com.belatrix.events.domain.executor.impl.MainThreadImpl;
import com.belatrix.events.domain.executor.impl.ThreadExecutor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ThreadModule {


    @Provides @Singleton
    public MainThread getMainThread() {
        return new MainThreadImpl();
    }

    @Provides @Singleton
    public Executor getExecutor() {
        return new ThreadExecutor();
    }


}