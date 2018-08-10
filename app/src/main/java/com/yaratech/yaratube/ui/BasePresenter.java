package com.yaratech.yaratube.ui;

public interface BasePresenter<V extends BaseView> {

    void attachView(V view);

    void detachView(V view);

    boolean isAttached(V view);


}
