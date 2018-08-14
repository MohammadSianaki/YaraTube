package com.yaratech.yaratube.ui.home;

import com.yaratech.yaratube.data.model.HomeResponse;
import com.yaratech.yaratube.ui.base.BasePresenter;
import com.yaratech.yaratube.ui.base.BaseView;

public interface HomeContract {


    interface View extends BaseView {
        void showLoadedData(HomeResponse response);
    }


    interface Presenter extends BasePresenter<View> {
        void fetchStoreItems();

        void cancelStoreApiRequest();
    }


}
