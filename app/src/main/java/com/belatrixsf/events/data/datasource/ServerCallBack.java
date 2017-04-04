package com.belatrixsf.events.data.datasource;

/**
 * Created by diegoveloper on 3/31/17.
 */

public interface ServerCallback<T> {
    void onSuccess(T response);
    void onFail(int statusCode, String errorMessage);
    void onError(String errorMessage);
}
