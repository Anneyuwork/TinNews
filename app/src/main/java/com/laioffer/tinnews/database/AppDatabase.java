package com.laioffer.tinnews.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.laioffer.tinnews.model.Article;
/*
Version Code: It specifies a current version. Once we introduce/modify the new version, we have to increase the version and define the migration strategy.
Entities: Current entities/tables Database contains
 */
@Database(entities = {Article.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract RoomDao dao();
}

