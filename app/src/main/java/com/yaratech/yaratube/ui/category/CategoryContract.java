package com.yaratech.yaratube.ui.category;

import com.yaratech.yaratube.ui.base.BasePresenter;
import com.yaratech.yaratube.ui.base.BaseView;

import java.util.List;

public interface CategoryContract {


    interface View extends BaseView {
        void showLoadedData(List list);

        void showDataNotAvailableToast();

        void showNetworkNotAvailableToast();

        void showProgressBarLoading();

        void finishProgressBarLoading();

    }


    interface Presenter extends BasePresenter<View> {

        void fetchCategoriesFromRemoteDataSource();

    }


}
