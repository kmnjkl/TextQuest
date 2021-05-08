package com.lkjuhkmnop.textquest.tqmanager;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {DBGame.class, DBQuest.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DBGamesDao gamesDao();
    public abstract DBQuestsDao questsDao();
}
