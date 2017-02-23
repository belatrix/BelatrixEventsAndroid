package com.belatrixsf.events.di.module;

import android.content.Context;

import com.belatrixsf.events.di.scope.ApplicationScope;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {

    private final Context context;

    public ContextModule(Context context) {
        this.context = context.getApplicationContext();
    }

    @Provides
    @ApplicationScope
    public Context context() {
        return context;
    }

}