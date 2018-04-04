package com.belatrix.events.data.datasource.rest.retrofit.base;

import org.json.JSONObject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by diegoveloper on 4/1/17.
 */

public abstract class BaseRepository {

    protected Observable subscribeOn(Observable observable){
        return observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

}
