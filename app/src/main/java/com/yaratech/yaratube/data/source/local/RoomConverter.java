package com.yaratech.yaratube.data.source.local;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class RoomConverter {

    @TypeConverter
    public static String fromUser(User user) {
        if (user == null) {
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<User>() {
        }.getType();
        return gson.toJson(user, type);
    }

    @TypeConverter
    public static User fromJsonUser(String json) {
        if (json == null) {
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<User>() {
        }.getType();
        return gson.fromJson(json, type);
    }
}
