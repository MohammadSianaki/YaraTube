package com.yaratech.yaratube.data.source.local.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.yaratech.yaratube.data.model.db.User;
import com.yaratech.yaratube.data.model.db.UserLoginInfo;

@Database(entities = {UserLoginInfo.class, User.class}, version = 1, exportSchema = false)
@TypeConverters({RoomConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;
    public final static String DATABASE_NAME = "user";

    public abstract UserDao userDao();

    public static AppDatabase getINSTANCE(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.
                    databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                    .build();

        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

}
