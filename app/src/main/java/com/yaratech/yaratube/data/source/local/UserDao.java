package com.yaratech.yaratube.data.source.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import io.reactivex.Completable;
import io.reactivex.Maybe;

@Dao
public interface UserDao {


    @Query("SELECT * FROM userLoginInfo ")
    Maybe<UserLoginInfo> getUserLoginInfo();

    @Insert
    Completable insertUserLoginInfo(UserLoginInfo userLoginInfo);

    @Delete
    Completable deleteUserLoginInfo(UserLoginInfo userLoginInfo);

}
