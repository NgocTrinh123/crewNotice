package com.crewcloud.apps.crewnotice.base;

/**
 * Created by tunglam on 12/17/16.
 */

public interface IPresenter<V> {
    void attachView(V mvpView);

    void detachView();
}
