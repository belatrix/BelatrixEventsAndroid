package com.belatrixsf.events.di.module;


import com.belatrixsf.events.di.scope.UIScope;
import com.belatrixsf.events.presentation.presenters.LoginPresenter;
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
    @UIScope
    public LoginPresenter.View view() {
        return view;
    }

    
}