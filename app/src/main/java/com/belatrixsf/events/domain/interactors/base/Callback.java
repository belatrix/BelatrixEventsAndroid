package com.belatrixsf.events.domain.interactors.base;

public interface Callback<T> {
        void onResult(T result);
        void onError(String errorMessage);
    }