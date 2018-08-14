package com.yaratech.yaratube.ui.base;

public interface BaseView {
    void showDataNotAvailableToast();

    void showNetworkNotAvailableToast();

    void showProgressBarLoading();

    void finishProgressBarLoading();
}
