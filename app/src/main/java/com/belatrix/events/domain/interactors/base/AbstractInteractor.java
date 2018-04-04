package com.belatrix.events.domain.interactors.base;

import io.reactivex.disposables.Disposable;

public abstract class AbstractInteractor {

    protected Disposable disposable;

    public void cancel(){
        if (disposable != null && !disposable.isDisposed()){
            disposable.dispose();
        }
    }
}
