package com.belatrix.events;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.belatrix.events.di.component.ApplicationComponent;
import com.belatrix.events.di.component.DaggerApplicationComponent;
import com.belatrix.events.di.module.ApplicationModule;

import timber.log.Timber;
import timber.log.Timber.DebugTree;

public class BxEventsApplication extends Application {

    private static Context context;
    ApplicationComponent component;

    public static Context getContext() {
        return context;
    }

//    private  void configGlide() {
//        Glide.get(this).register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory());
//    }

    public static BxEventsApplication get(Activity activity) {
        return (BxEventsApplication) activity.getApplication();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        configLogger();
        configDagger();
//        configGlide();
    }

    private void configLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new DebugTree());
        }
    }

    private void configDagger() {
        component = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
        component.inject(this);
    }

    public ApplicationComponent getComponent() {
        return component;
    }
}
