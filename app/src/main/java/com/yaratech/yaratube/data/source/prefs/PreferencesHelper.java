package com.yaratech.yaratube.data.source.prefs;

public interface PreferencesHelper {

    void setUserMobilePhoneNumber(String mobilePhoneNumber);

    String getUserMobilePhoneNumber();

    void setUserLoginStep(int loginStep);

    int getUserLoginStep();

    void saveUserProfileImageAvatarPath(String imagePath);

    String loadUserProfileImageAvatarPath();
}
