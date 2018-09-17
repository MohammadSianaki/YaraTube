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
    }


    interface Presenter extends BasePresenter<View> {

        String getUserProfileImageAvatarPath();

        void saveUserProfileImageAvatarPath(String imagePath);

        void uploadUserProfileInfo(String name, String birthday, String gender);

        void uploadUserProfileImageAvatar(File file);

        void loadUserProfileInfo();
    }

}
