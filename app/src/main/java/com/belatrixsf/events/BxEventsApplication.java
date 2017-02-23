package com.belatrixsf.events;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.belatrixsf.events.di.component.ApplicationComponent;
import com.belatrixsf.events.di.module.ContextModule;

import timber.log.Timber;
import timber.log.Timber.DebugTree;

public class BxEventsApplication extends Application {

    private static Context context;
    ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        configLogger();
        configDagger();
    }

    private void configLogger(){
        if (BuildConfig.DEBUG) {
            Timber.plant(new DebugTree());
        }
    }

    private void configDagger(){
        //component = DaggerApplicationComponent.builder().contextModule(new ContextModule(this)).build();
    }

    public static Context getContext() {
        return context;
    }

    public  ApplicationComponent getComponent() {
        return component;
    }

    public static BxEventsApplication get(Activity activity) {
        return (BxEventsApplication) activity.getApplication();
    }
}
