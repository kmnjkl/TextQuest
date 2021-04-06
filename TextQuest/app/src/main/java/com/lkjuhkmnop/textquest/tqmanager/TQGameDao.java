package com.lkjuhkmnop.textquest.tqmanager;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TQGameDao {
    @Query("SELECT * FROM tqgame")
    List<TQGame> getAllGames();

    @Query("SELECT * FROM tqgame WHERE game_id = (:gameId)")
    TQGame getGameById(int gameId);

    @Query("SELECT * FROM tqgame WHERE game_title = (:gameTitle)")
    List<TQGame> getGamesByTitle(String gameTitle);

    @Insert
    void insertAll(TQGame... games);

    @Delete
    void deleteGames(TQGame... games);
}
