package com.beknumonov.noteapp2.base;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class RequestCallback<T> implements Callback<T> {

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            onResponseSuccess(call, response);
        } else {
            onResponseFail(call, new Throwable(response.code() + ": Failed"));
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        onResponseFail(call, t);
    }

    protected abstract void onResponseSuccess(Call<T> call, Response<T> response);

    protected abstract void onResponseFail(Call<T> call, Throwable t);
}
