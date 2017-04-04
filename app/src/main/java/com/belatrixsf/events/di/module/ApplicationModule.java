package com.belatrixsf.events.di.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.belatrixsf.events.data.datasource.memory.InMemoryRepository;
import com.belatrixsf.events.domain.repository.Repository;
import com.belatrixsf.events.utils.cache.Cache;
import com.belatrixsf.events.utils.cache.impl.CachePreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final Context context;

    Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
        this.context = application.getApplicationContext();
    }

    @Provides
    @Singleton
    public Context context() {
        return context;
    }

    @Provides
    @Singleton
    public Repository repository (){
        return new InMemoryRepository();
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences(Application application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    @Provides
    @Singleton
    Cache provideCache(SharedPreferences sharedPreferences) {
        Cache cache = new CachePreferences(sharedPreferences);
        return cache;
    }

}