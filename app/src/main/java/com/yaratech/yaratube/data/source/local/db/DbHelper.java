package com.yaratech.yaratube.data.source.local.db;

import com.yaratech.yaratube.data.DataManager;
import com.yaratech.yaratube.data.model.db.User;

import io.reactivex.disposables.Disposable;

public interface DbHelper {


    Disposable isUserAuthorized(DataManager.LoginDatabaseResultCallback callback);

    Disposable saveUserToDb(User user, DataManager.SaveUserDatabaseResultCallback callback);

    Disposable clearDatabase(DataManager.LoginDatabaseResultCallback loginDatabaseResultCallback);

}
