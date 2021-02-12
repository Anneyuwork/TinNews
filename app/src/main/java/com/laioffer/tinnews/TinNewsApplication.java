package com.laioffer.tinnews;

import android.app.Application;

import androidx.room.Room;


import com.laioffer.tinnews.database.AppDatabase;

public class TinNewsApplication extends Application {

    private static AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "tin_db").build();
    }

    public static AppDatabase getDatabase() {
        return database;
    }
}

