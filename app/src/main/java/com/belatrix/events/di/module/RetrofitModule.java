package com.belatrix.events.di.module;

import com.belatrix.events.BuildConfig;
import com.belatrix.events.data.datasource.rest.retrofit.api.DeviceAPI;
import com.belatrix.events.data.datasource.rest.retrofit.api.EmployeeAPI;
import com.belatrix.events.data.datasource.rest.retrofit.api.EventAPI;
import com.belatrix.events.data.datasource.rest.retrofit.api.IdeaAPI;
import com.belatrix.events.data.datasource.rest.retrofit.api.NotificationAPI;
import com.belatrix.events.data.datasource.rest.retrofit.api.UserAPI;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by diegoveloper on 3/31/17.
 */

@Module
public class RetrofitModule {

    @Singleton
    @Provides
    public Retrofit provideRetrofit() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        }
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BuildConfig.BASE_URL)
                .client(client)
                .build();
    }

    @Singleton
    @Provides
    public EventAPI provideEventAPI(Retrofit retrofit) {
        return retrofit.create(EventAPI.class);
    }

    @Singleton
    @Provides
    public EmployeeAPI provideEmployeeAPI(Retrofit retrofit) {
        return retrofit.create(EmployeeAPI.class);
    }

    @Singleton
    @Provides
    public DeviceAPI provideDeviceAPI(Retrofit retrofit) {
        return retrofit.create(DeviceAPI.class);
    }

    @Singleton
    @Provides
    public NotificationAPI provideNotificationAPI(Retrofit retrofit) {
        return retrofit.create(NotificationAPI.class);
    }

    @Singleton
    @Provides
    public UserAPI provideUserAPI(Retrofit retrofit) {
        return retrofit.create(UserAPI.class);
    }

    @Singleton
    @Provides
    public IdeaAPI providesIdeaAPI(Retrofit retrofit) {
        return retrofit.create(IdeaAPI.class);
    }
}
