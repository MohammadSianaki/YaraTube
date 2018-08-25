package com.yaratech.yaratube.data.source;

import com.yaratech.yaratube.data.source.local.LocalDataSource;
import com.yaratech.yaratube.data.source.local.UserLoginInfo;
import com.yaratech.yaratube.data.source.prefs.AppPreferencesHelper;
import com.yaratech.yaratube.data.source.prefs.PreferencesHelper;
import com.yaratech.yaratube.data.source.remote.UserRemoteDataSource;

public class UserRepository implements UserDataSource {

    private static UserRepository INSTANCE = null;
    private UserRemoteDataSource remoteUserDataSource;
    private LocalDataSource localUserDataSource;
    private AppPreferencesHelper appPreferencesHelper;

    private UserRepository(UserDataSource remoteUserDataSource, UserDataSource localUserDataSource, PreferencesHelper preferencesHelper) {
        //no instance
        if (remoteUserDataSource instanceof UserRemoteDataSource) {
            this.remoteUserDataSource = (UserRemoteDataSource) remoteUserDataSource;
        } else {
            throw new ClassCastException("IS NOT INSTANCE OF UserRemoteDataSource");
        }

        if (localUserDataSource instanceof LocalDataSource) {
            this.localUserDataSource = (LocalDataSource) localUserDataSource;
        } else {
            throw new ClassCastException("IS NOT INSTANCE OF LocalDataSource");

        }
        appPreferencesHelper = (AppPreferencesHelper) preferencesHelper;
    }


    public static UserRepository getINSTANCE(UserDataSource userDataSource,
                                             LocalDataSource localDataSource,
                                             PreferencesHelper preferencesHelper) {
        if (INSTANCE == null) {
            INSTANCE = new UserRepository(userDataSource, localDataSource, preferencesHelper);
        }
        return INSTANCE;
    }


    @Override
    public void registerUserWithThisPhoneNumber(ApiResultCallback callback, String phoneNumber) {
        remoteUserDataSource.registerUserWithThisPhoneNumber(callback, phoneNumber);
    }

    @Override
    public void verifyUserWithThisCode(ApiResultCallback callback, String code, String phoneNumber) {
        remoteUserDataSource.verifyUserWithThisCode(callback, code, phoneNumber);
    }


    // Todo in below method call @link{} localDataSource methods

    @Override
    public void insertUserLoginInfo(InsertIntoDatabaseCallback callback, UserLoginInfo userLoginInfo) {
        localUserDataSource.insertUserLoginInfo(callback, userLoginInfo);
    }

    @Override
    public void checkIfUserIsAuthorized(ReadFromDatabaseCallback callback) {
        localUserDataSource.checkIfUserIsAuthorized(callback);
    }

    @Override
    public void setUserMobilePhoneNumber(String mobilePhoneNumber) {
        appPreferencesHelper.setUserMobilePhoneNumber(mobilePhoneNumber);
    }

    @Override
    public String getUserMobilePhoneNumber() {
        return appPreferencesHelper.getUserMobilePhoneNumber();
    }

    @Override
    public void setUserLoginStep(int loginStep) {
        appPreferencesHelper.setUserLoginStep(loginStep);
    }

    @Override
    public int getUserLoginStep() {
        return appPreferencesHelper.getUserLoginStep();
    }
}
