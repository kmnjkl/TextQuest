package com.lkjuhkmnop.textquest.tqmanager;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
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

    @ColumnInfo(name = "character_properties")
    private String characterProperties;

    @ColumnInfo(name = "character_parameters")
    private String characterParameters;

    @ColumnInfo(name = "quest_json")
    private String questJson;

    @Ignore
    private boolean uploadedToCloud = false;
    public boolean isUploadedToCloud() {
        return uploadedToCloud;
    }
    public void setUploadedToCloud(boolean uploadedToCloud) {
        this.uploadedToCloud = uploadedToCloud;
    }

    @Ignore
    private String uploaderUserId;
    public String getUploaderUserId() {
        return uploaderUserId;
    }
    public void setUploaderUserId(String uploaderUserId) {
        this.uploaderUserId = uploaderUserId;
    }

    public DBQuest(String questTitle, String questAuthor, String characterProperties, String characterParameters, String questJson) {
        this.questTitle = questTitle;
        this.questAuthor = questAuthor;
        this.characterProperties = characterProperties;
        this.characterParameters = characterParameters;
        this.questJson = questJson;
    }

    @Ignore
    public DBQuest() {}

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

    public String getCharacterProperties() {
        return characterProperties;
    }

    public void setCharacterProperties(String characterProperties) {
        this.characterProperties = characterProperties;
    }

    public String getCharacterParameters() {
        return characterParameters;
    }

    public void setCharacterParameters(String characterParameters) {
        this.characterParameters = characterParameters;
    }
}
