package com.lkjuhkmnop.textquest.tqmanager;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DBQuestDao {
    @Query("SELECT * FROM DBQuest")
    List<DBQuest> getAllQuests();

    @Query("SELECT quest_id, quest_title, quest_author FROM DBQuest")
    DBQuest[] getAllQuestsArray();

    @Query("SELECT * FROM DBQuest WHERE quest_id = (:questId)")
    DBQuest getQuestById(int questId);

    @Query("SELECT * FROM DBQuest WHERE quest_title = (:gameTitle)")
    List<DBQuest> getQuestsByTitle(String gameTitle);

    @Insert
    void insertAll(DBQuest... quests);

    @Delete
    void deleteQuests(DBQuest... quests);
}
