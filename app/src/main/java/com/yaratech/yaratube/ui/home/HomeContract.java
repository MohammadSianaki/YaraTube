package com.yaratech.yaratube.ui.home;

import com.yaratech.yaratube.data.model.api.StoreResponse;
import com.yaratech.yaratube.ui.base.BasePresenter;
import com.yaratech.yaratube.ui.base.BaseView;

public interface HomeContract {


    interface View extends BaseView {
        void showLoadedData(StoreResponse response);

        void showDataNotAvailableToast();

        void showNetworkNotAvailableToast();

        void showProgressBarLoading();

        void finishProgressBarLoading();
    }


    interface Presenter extends BasePresenter<View> {
        void fetchStoreItems();

        void cancelStoreApiRequest();
    }


}
