package com.belatrixsf.events.data.datasource.rest.retrofit.base;

import com.belatrixsf.events.data.datasource.ServerCallback;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by diegoveloper on 4/1/17.
 */

public abstract class BaseRepository {

    protected <T> void executeRequest(ServerCallback<T> callBack, Call<T> call) {
        try {
            Response<T> response = call.execute();
            if (response.isSuccessful() && response.body() != null) {
                T data = response.body();
                callBack.onSuccess(data);
            } else {
                int statusCode = response.code();
                JSONObject jObjError = new JSONObject(response.errorBody().string());
                callBack.onFail(statusCode, jObjError.getString("message"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            callBack.onError(e.getMessage());
        }
    }

}
