package com.lkjuhkmnop.textquest.tqmanager;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class TQGame {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "game_id")
    private int gameId;

    @ColumnInfo(name = "quest_id")
    private int questId;

    @ColumnInfo(name = "game_title")
    private String gameTitle;

    @ColumnInfo(name = "game_time")
    private long gameTimestamp;

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getQuestId() {
        return questId;
    }

    public void setQuestId(int questId) {
        this.questId = questId;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    public long getGameTimestamp() {
        return gameTimestamp;
    }

    public void setGameTimestamp(long gameTimestamp) {
        this.gameTimestamp = gameTimestamp;
    }
}
