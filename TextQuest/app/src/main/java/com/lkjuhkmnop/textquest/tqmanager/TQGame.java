package com.lkjuhkmnop.textquest.tqmanager;

import java.util.Calendar;
import java.util.Locale;

public class TQGame {
    private String gameTitle;
    private Calendar gameTime;

    public String getGameTitle() {
        return gameTitle;
    }

    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    public Calendar getGameTime() {
        return gameTime;
    }

    public void setGameTime(Calendar gameTime) {
        this.gameTime = gameTime;
    }
}
