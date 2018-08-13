package com.yaratech.yaratube.ui.base;

public interface BasePresenter<V extends BaseView> {

    void attachView(V view);

    void detachView(V view);

    boolean isAttached();


}