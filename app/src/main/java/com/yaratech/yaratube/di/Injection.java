package com.yaratech.yaratube.di;

import android.arch.persistence.room.Room;

import com.yaratech.yaratube.data.source.local.db.AppDatabase;

import static com.yaratech.yaratube.data.source.local.db.AppDatabase.DATABASE_NAME;

public class Injection {

    private static AppDatabase appDatabase = null;

    private Injection() {
        //no instance
    }

    public static AppDatabase provideAppDatabase() {
        if (appDatabase == null) {
            appDatabase = Room.
                    databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                    .build();

        }
        return appDatabase;
    }

}
