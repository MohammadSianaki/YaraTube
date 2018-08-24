package com.yaratech.yaratube.data.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

@Database(entities = {UserLoginInfo.class, User.class}, version = 1)
@TypeConverters({RoomConverter.class})
public abstract class AppDataBase extends RoomDatabase {

    private static AppDataBase INSTANCE;
    private final static String DATABASE_NAME = "user";

    public abstract UserDao userDao();

    public static AppDataBase getINSTANCE(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.
                    databaseBuilder(context.getApplicationContext(), AppDataBase.class, DATABASE_NAME)
                    .build();

        }
        return INSTANCE;
    }

    public static AppDataBase getMemoryDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.inMemoryDatabaseBuilder(context.getApplicationContext(), AppDataBase.class)
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

}
