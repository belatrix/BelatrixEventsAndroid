package com.belatrix.events.data.datasource.rest.retrofit.base;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by diegoveloper on 4/1/17.
 */

public abstract class BaseRepository {

    protected Observable subscribeOn(Observable observable) {
        return observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

}
