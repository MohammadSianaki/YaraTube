package com.yaratech.yaratube.data.source.local;

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

    @ColumnInfo(name = "token")
    private String token;

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
