package com.yaratech.yaratube.ui.productdetails;

import com.yaratech.yaratube.data.model.Product;
import com.yaratech.yaratube.ui.base.BasePresenter;
import com.yaratech.yaratube.ui.base.BaseView;

public interface DetailsContract {

    public interface View extends BaseView {
        void showLoadedData(Product product);

        void showDataNotAvailableToast();

        void showNetworkNotAvailableToast();

        void showProgressBarLoading();

        void finishProgressBarLoading();
    }

    public interface Presenter extends BasePresenter<View> {
        void fetchProductDetails(int productId);

        void cancelProductDetailsApiRequest();
    }

}
