package com.lkjuhkmnop.textquest.tqmanager;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DBGamesDao {
    @Query("SELECT * FROM DBGame")
    List<DBGame> getAllGames();

    @Query("SELECT * FROM DBGame WHERE game_id = (:gameId)")
    DBGame getGameById(int gameId);

    @Query("SELECT * FROM DBGame WHERE game_title = (:gameTitle)")
    DBGame getGameByTitle(String gameTitle);

    @Insert
    void insert(DBGame... games);

    @Delete
    void deleteGames(DBGame... games);
}
