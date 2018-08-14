package com.yaratech.yaratube.ui.productdetails;

import com.yaratech.yaratube.data.model.Product;
import com.yaratech.yaratube.ui.base.BasePresenter;
import com.yaratech.yaratube.ui.base.BaseView;

public interface DetailsContract {

    interface View extends BaseView {
        void showLoadedData(Product product);
    }

    interface Presenter extends BasePresenter<View> {
        void fetchProductDetails(int productId);

        void cancelProductDetailsApiRequest();
    }

}
