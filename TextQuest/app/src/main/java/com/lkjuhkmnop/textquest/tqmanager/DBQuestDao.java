package com.lkjuhkmnop.textquest.tqmanager;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DBQuestDao {
    @Query("SELECT * FROM dbquest")
    List<DBQuest> getAllQuests();

    @Query("SELECT * FROM dbquest WHERE quest_id = (:questId)")
    DBQuest getQuestById(int questId);

    @Query("SELECT * FROM DBQuest WHERE quest_title = (:gameTitle)")
    List<DBQuest> getQuestsByTitle(String gameTitle);

    @Insert
    void insertAll(DBQuest... quests);

    @Delete
    void deleteQuests(DBQuest... quests);
}
