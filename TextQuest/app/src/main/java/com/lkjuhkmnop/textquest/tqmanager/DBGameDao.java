package com.lkjuhkmnop.textquest.tqmanager;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DBGameDao {
    @Query("SELECT * FROM DBGame")
    List<DBGame> getAllGames();

    @Query("SELECT * FROM DBGame WHERE game_id = (:gameId)")
    DBGame getGameById(int gameId);

    @Query("SELECT * FROM DBGame WHERE game_title = (:gameTitle)")
    List<DBGame> getGamesByTitle(String gameTitle);

    @Insert
    void insertAll(DBGame... games);

    @Delete
    void deleteGames(DBGame... games);
}
