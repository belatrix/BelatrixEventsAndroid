package com.belatrixsf.events.di.module;

import android.content.Context;

import com.belatrixsf.events.data.datasource.InMemoryRepository;
import com.belatrixsf.events.domain.repository.Repository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final Context context;

    public ApplicationModule(Context context) {
        this.context = context;
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


}