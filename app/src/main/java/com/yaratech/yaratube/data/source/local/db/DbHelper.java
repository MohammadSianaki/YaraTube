package com.yaratech.yaratube.data.source.local.db;

import com.yaratech.yaratube.data.DataManager;
import com.yaratech.yaratube.data.model.db.UserLoginInfo;

import io.reactivex.disposables.Disposable;

public interface DbHelper {


    Disposable isUserAuthorized(DataManager.LoginDatabaseResultCallback callback);

    Disposable saveUserLoginInfo(UserLoginInfo userLoginInfo, DataManager.SaveUserDatabaseResultCallback callback);

    Disposable clearDatabase(DataManager.LoginDatabaseResultCallback loginDatabaseResultCallback);

}
