package com.belatrix.events;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.belatrix.events.di.component.ApplicationComponent;
import com.belatrix.events.di.component.DaggerApplicationComponent;
import com.belatrix.events.di.module.ApplicationModule;
import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;

import java.io.InputStream;

import timber.log.Timber;
import timber.log.Timber.DebugTree;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class BxEventsApplication extends Application {

    private static Context context;
    ApplicationComponent component;

    public static Context getContext() {
        return context;
    }

    public static BxEventsApplication get(Activity activity) {
        return (BxEventsApplication) activity.getApplication();
    }

    private void configGlide() {
        Glide.get(this).register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        configLogger();
        configDagger();
        configGlide();
        configCalligraphy();
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

    private void configCalligraphy() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/OpenSans-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
    }

    public ApplicationComponent getComponent() {
        return component;
    }
}
