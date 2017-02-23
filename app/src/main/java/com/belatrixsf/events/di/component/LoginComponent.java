package com.belatrixsf.events.di.component;

import com.belatrixsf.events.di.module.LoginModule;
import com.belatrixsf.events.di.module.ThreadModule;
import com.belatrixsf.events.di.scope.UIScope;
import com.belatrixsf.events.presentation.presenters.LoginPresenter;
import com.belatrixsf.events.presentation.ui.fragments.LoginFragment;

import dagger.Component;

/**
 * Created by dvelasquez on 2/23/17.
 */
@UIScope
@Component(modules = LoginModule.class, dependencies = ThreadModule.class)
public interface LoginComponent {

    void inject(LoginFragment loginFragment);
    void inject(LoginPresenter loginPresenter);
}
