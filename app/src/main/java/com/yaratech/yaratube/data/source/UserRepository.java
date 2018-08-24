package com.yaratech.yaratube.data.source;

import com.yaratech.yaratube.data.source.local.UserLoginInfo;
import com.yaratech.yaratube.data.source.remote.UserRemoteDataSource;

public class UserRepository implements UserDataSource {

    private static UserRepository INSTANCE = null;
    private UserDataSource userDataSource;

    private UserRepository(UserDataSource userDataSource) {
        //no instance
        if (userDataSource instanceof UserRemoteDataSource) {
            this.userDataSource = (UserRemoteDataSource) userDataSource;
        } else {
            throw new ClassCastException("IS NOT INSTANCE OF UserRemoteDataSource");
        }
    }


    public static UserRepository getINSTANCE(UserDataSource userDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new UserRepository(userDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void checkIfUserIsAuthorized(DatabaseResultCallback callback) {

    }

    @Override
    public void insertUserLoginInfo(DatabaseResultCallback callback, UserLoginInfo userLoginInfo) {

    }

    @Override
    public void registerUserWithThisPhoneNumber(ApiResultCallback callback, String phoneNumber) {
        userDataSource.registerUserWithThisPhoneNumber(callback, phoneNumber);
    }

    @Override
    public void verifyUserWithThisCode(ApiResultCallback callback, String code, String phoneNumber) {
        userDataSource.verifyUserWithThisCode(callback, code, phoneNumber);
    }
}
