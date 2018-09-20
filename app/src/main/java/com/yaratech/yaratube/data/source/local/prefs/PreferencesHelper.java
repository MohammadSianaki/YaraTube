package com.yaratech.yaratube.data.source.local.prefs;

public interface PreferencesHelper {

    void setUserMobilePhoneNumber(String mobilePhoneNumber);

    String getUserMobilePhoneNumber();

    void setUserLoginStep(int loginStep);

    int getUserLoginStep();

    void setUserProfileImageAvatarPath(String imagePath);

    String getUserProfileImageAvatarPath();

    String getUserTokenApi();

    void setUserTokenApi(String token);

    void clearPreferences();
}
