package com.crewcloud.apps.crewnotice.base;

import android.support.annotation.NonNull;

import com.crewcloud.apps.crewnotice.dtos.ErrorDto;

import rx.Observer;

/**
 * Created by mb on 3/18/16
 */
public abstract class ResponseListener<T> implements Observer<T> {

    @Override
    public final void onCompleted() {
    }

    @Override
    public void onError(Throwable error) {

        ErrorDto errorDto = new ErrorDto();
        if (null != error) {
            onError(errorDto);
        }

    }

    @Override
    public void onNext(T s) {
        onSuccess(s);
    }

    public abstract void onSuccess(T result);

    public abstract void onError(@NonNull ErrorDto messageResponse);
}
