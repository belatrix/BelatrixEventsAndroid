package com.belatrixsf.events.di.component;

import com.belatrixsf.events.di.module.LoginModule;
import com.belatrixsf.events.di.scope.UIScope;
import com.belatrixsf.events.presentation.ui.fragments.LoginFragment;

import dagger.Component;
import dagger.Subcomponent;

/**
 * Created by dvelasquez on 2/23/17.
 */
@UIScope
@Subcomponent(
        modules = {
                LoginModule.class,
        })
public interface LoginComponent {

    void inject(LoginFragment fragment);
}
