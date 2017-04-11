package com.belatrix.events.di.module;


import android.app.Activity;

import dagger.Module;
import dagger.Provides;

@Module
public class UIModule {

    private  Activity activity;

    public UIModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    Activity provideActivity() {
        return activity;
    }


}