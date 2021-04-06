package com.lkjuhkmnop.roomtest;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {MyEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MyDao myDao();
}
