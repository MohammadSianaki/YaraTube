package com.yaratech.yaratube.data.model.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "userLoginInfo")
public class UserLoginInfo {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "isAuthorized")
    private int isAuthorized;

    @ColumnInfo(name = "user")
    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsAuthorized() {
        return isAuthorized;
    }

    public void setIsAuthorized(int isAuthorized) {
        this.isAuthorized = isAuthorized;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
