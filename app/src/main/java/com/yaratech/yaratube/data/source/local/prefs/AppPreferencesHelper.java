package com.yaratech.yaratube.data.source.local.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.yaratech.yaratube.data.model.other.Event;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class AppPreferencesHelper implements PreferencesHelper {

    private static final String PREF_KEY_CURRENT_USER_MOBILE_PHONE_NUMBER = "PREF_KEY_CURRENT_USER_MOBILE_PHONE_NUMBER";
    private static final String PREF_KEY_CURRENT_USER_LOGIN_STEP = "PREF_KEY_CURRENT_USER_LOGIN_STEP";
    private static final String PREF_KEY_CURRENT_USER_PROFILE_IMAGE_AVATAR_PATH = "PREF_KEY_CURRENT_USER_PROFILE_IMAGE_AVATAR_PATH";
    private final SharedPreferences mPrefs;


    public AppPreferencesHelper(Context context) {
        this.mPrefs = getDefaultSharedPreferences(context);
    }

    @Override
    public void setUserMobilePhoneNumber(String mobilePhoneNumber) {
        mPrefs.
                edit()
                .putString(PREF_KEY_CURRENT_USER_MOBILE_PHONE_NUMBER, mobilePhoneNumber)
                .apply();
    }

    @Override
    public String getUserMobilePhoneNumber() {

        return mPrefs.getString(PREF_KEY_CURRENT_USER_MOBILE_PHONE_NUMBER, null);
    }

    @Override
    public void setUserLoginStep(int loginStep) {
        mPrefs
                .edit()
                .putInt(PREF_KEY_CURRENT_USER_LOGIN_STEP, loginStep)
                .apply();

    }

    @Override
    public int getUserLoginStep() {
        return mPrefs.getInt(PREF_KEY_CURRENT_USER_LOGIN_STEP, Event.LOGIN_STEP_ONE);
    }

    @Override
    public void setUserProfileImageAvatarPath(String imagePath) {
        mPrefs
                .edit()
                .putString(PREF_KEY_CURRENT_USER_PROFILE_IMAGE_AVATAR_PATH, imagePath)
                .apply();
    }

    @Override
    public String getUserProfileImageAvatarPath() {
        return mPrefs.getString(PREF_KEY_CURRENT_USER_PROFILE_IMAGE_AVATAR_PATH, null);
    }
}
