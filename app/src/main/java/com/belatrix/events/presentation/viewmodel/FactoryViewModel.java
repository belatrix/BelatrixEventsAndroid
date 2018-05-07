package com.belatrix.events.presentation.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import javax.inject.Inject;
import javax.inject.Singleton;

public class FactoryViewModel implements ViewModelProvider.Factory {

    private final EventViewModel mEventViewModel;

    public FactoryViewModel(EventViewModel eventViewModel) {
        this.mEventViewModel = eventViewModel;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(EventViewModel.class)) {
            return (T) mEventViewModel;
        }
        throw new IllegalArgumentException("Unknown class name");
    }
}
