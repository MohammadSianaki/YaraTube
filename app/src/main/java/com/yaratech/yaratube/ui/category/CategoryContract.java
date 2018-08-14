package com.yaratech.yaratube.ui.category;

import com.yaratech.yaratube.ui.base.BasePresenter;
import com.yaratech.yaratube.ui.base.BaseView;

import java.util.List;

public interface CategoryContract {


    interface View extends BaseView {
        void showLoadedData(List list);
    }


    interface Presenter extends BasePresenter<View> {

        void fetchCategoriesFromRemoteDataSource();

        void cancelCategoryApiRequest();
    }


}
