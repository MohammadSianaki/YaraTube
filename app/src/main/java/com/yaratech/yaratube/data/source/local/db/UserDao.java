package com.yaratech.yaratube.data.source.local.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.yaratech.yaratube.data.model.db.UserLoginInfo;

@Dao
public interface UserDao {


    @Query("SELECT * FROM userLoginInfo ")
    UserLoginInfo getUserLoginInfo();

    @Insert
    void insertUserLoginInfo(UserLoginInfo userLoginInfo);

    @Delete
    void deleteUserLoginInfo(UserLoginInfo userLoginInfo);

}
