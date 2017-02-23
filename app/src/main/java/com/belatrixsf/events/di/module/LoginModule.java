package com.belatrixsf.events.di.module;

import android.app.Activity;
import android.content.Context;

import com.belatrixsf.events.di.scope.ApplicationScope;
import com.belatrixsf.events.di.scope.UIScope;
import com.belatrixsf.events.domain.interactors.LoginInteractor;
import com.belatrixsf.events.presentation.presenters.LoginPresenter;
import com.belatrixsf.events.presentation.ui.activities.LoginActivity;
import com.belatrixsf.events.presentation.ui.fragments.LoginFragment;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginModule {

    private final LoginFragment loginFragment;
    private final LoginPresenter.View view;

    public LoginModule(LoginFragment loginFragment) {
        this.loginFragment = loginFragment;
        this.view = loginFragment;
    }


    @Provides
    @UIScope
    public LoginFragment loginFragment() {
        return loginFragment;
    }


    @Provides
    public LoginPresenter.View view() {
        return loginFragment;
    }


}