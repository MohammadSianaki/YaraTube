package com.yaratech.yaratube.ui.profile;

import com.yaratech.yaratube.data.model.api.GetProfileResponse;
import com.yaratech.yaratube.ui.base.BasePresenter;
import com.yaratech.yaratube.ui.base.BaseView;

import java.io.File;

public interface ProfileContract {

    interface View extends BaseView {

        void showDataNotAvailableMessage();

        void showSubmitSuccessfulMessage();

        void showLoadedUserProfileInformation(GetProfileResponse getProfileResponse);

        void loadImageAvatarAfterUpload(String path);

        void showSuccessfulLogoutMessage();

        void closeProfileFragment();
    }


    interface Presenter extends BasePresenter<View> {
        void logout();

        void uploadUserProfileInfo(String name, String birthday, String gender);

        void uploadUserProfileImageAvatar(File file);

        void loadUserProfileInfo();
    }

}
