package com.yaratech.yaratube.data.source.local.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.yaratech.yaratube.data.model.db.User;

@Dao
public interface UserDao {


    @Query("SELECT * FROM user ")
    User getUserFromDb();

    @Insert
    void saveUserToDb(User user);

    @Delete
    void deleteUserFromDb(User user);

}
