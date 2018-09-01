package com.yaratech.yaratube.ui.gridcategory;

import com.yaratech.yaratube.ui.base.BasePresenter;
import com.yaratech.yaratube.ui.base.BaseView;

import java.util.List;

public interface GridCategoryContract {


    interface View extends BaseView {
        void showLoadedData(List list);

        void showDataNotAvailableToast();

        void showNetworkNotAvailableToast();

        void showProgressBarLoading();

        void finishProgressBarLoading();
    }


    interface Presenter extends BasePresenter<View> {
        void fetchProducts(int id, int offset, int limit);

        void cancelProductApiRequest();
    }
}

