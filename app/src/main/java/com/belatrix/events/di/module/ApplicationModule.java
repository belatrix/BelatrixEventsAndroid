package com.belatrix.events.di.module;

import android.accounts.AccountManager;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.belatrix.events.R;
import com.belatrix.events.data.datasource.memory.InMemoryRepository;
import com.belatrix.events.domain.repository.Repository;
import com.belatrix.events.utils.account.AccountUtils;
import com.belatrix.events.utils.cache.Cache;
import com.belatrix.events.utils.cache.impl.CachePreferences;

import javax.inject.Named;
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
    public Repository repository() {
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

    @Provides
    @Singleton
    AccountManager providesAccountManager(Context context) {
        return AccountManager.get(context);
    }

    @Provides
    @Singleton
    @Named("account_type")
    String providesAccountType(Context context) {
        return context.getString(R.string.account_type);
    }

    @Provides
    @Singleton
    AccountUtils providesAccountUtils(AccountManager accountManager, @Named("account_type") String accountType) {
        return new AccountUtils(accountManager, accountType);
    }

}