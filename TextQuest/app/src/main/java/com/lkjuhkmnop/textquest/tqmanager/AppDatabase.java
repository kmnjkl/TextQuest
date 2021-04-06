package com.lkjuhkmnop.textquest.tqmanager;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = TQGame.class, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TQGameDao gameDao();
}
