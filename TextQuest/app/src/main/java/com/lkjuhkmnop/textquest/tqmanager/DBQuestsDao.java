package com.lkjuhkmnop.textquest.tqmanager;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DBQuestsDao {
    @Query("SELECT * FROM DBQuest")
    List<DBQuest> getAllQuests();

    @Query("SELECT quest_id, quest_cloud_id, quest_uploader_user_id, quest_title FROM DBQuest")
    DBQuest[] getAllQuestsArray();

    @Query("SELECT * FROM DBQuest WHERE quest_id = (:questId)")
    DBQuest getQuestById(int questId);

    @Query("SELECT * FROM DBQuest WHERE quest_title = (:gameTitle)")
    List<DBQuest> getQuestsByTitle(String gameTitle);

    @Insert
    void insert(DBQuest... quests);

    @Update
    void update(DBQuest... quests);

    @Query("UPDATE DBQuest SET quest_cloud_id = (:newCloudId), quest_uploader_user_id = (:newUploaderUserId) WHERE quest_id = (:questId)")
    void  updateCloudInfo(int questId, String newCloudId, String newUploaderUserId);

    @Query("UPDATE DBQuest SET quest_cloud_id = NULL, quest_uploader_user_id = NULL WHERE quest_id = (:questId)")
    void  updateCloudInfo(int questId);

    @Delete
    void deleteQuests(DBQuest... quests);

    @Query("DELETE FROM DBQuest WHERE quest_id IN (:ids)")
    void deleteQuestsByIds(int... ids);
}
