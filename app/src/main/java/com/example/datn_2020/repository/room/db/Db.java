package com.example.datn_2020.repository.room.db;

import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.datn_2020.repository.room.App;

public class Db {
    private static AppDatabase datnDatabase;
    private final static String DATABASE_NAME = "datn_database";

    public static AppDatabase getDB(){
        if(datnDatabase == null){
            datnDatabase = Room.databaseBuilder(App.INSTANCE, AppDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .addMigrations(MIGRATION_1_2)
                    .build();
        }
        return datnDatabase;
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("DROP TABLE username;");
        }
    };

}
