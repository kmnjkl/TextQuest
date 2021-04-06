package com.lkjuhkmnop.textquest.tqmanager;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class DBQuest {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "quest_id")
    private int questId;

    @ColumnInfo(name = "quest_title")
    private String questTitle;

    @ColumnInfo(name = "quest_author")
    private String questAuthor;

    @ColumnInfo(name = "quest_json")
    private String questJson;

    public DBQuest(String questTitle, String questAuthor, String questJson) {
        this.questTitle = questTitle;
        this.questAuthor = questAuthor;
        this.questJson = questJson;
    }

    public int getQuestId() {
        return questId;
    }

    public void setQuestId(int questId) {
        this.questId = questId;
    }

    public String getQuestTitle() {
        return questTitle;
    }

    public void setQuestTitle(String questTitle) {
        this.questTitle = questTitle;
    }

    public String getQuestAuthor() {
        return questAuthor;
    }

    public void setQuestAuthor(String questAuthor) {
        this.questAuthor = questAuthor;
    }

    public String getQuestJson() {
        return questJson;
    }

    public void setQuestJson(String questJson) {
        this.questJson = questJson;
    }
}
