package com.lkjuhkmnop.textquest.tools;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lkjuhkmnop.textquest.gamesactivity.GamesActivity;
import com.lkjuhkmnop.textquest.libraryactivity.LibraryActivity;
import com.lkjuhkmnop.textquest.questmanageactivity.QuestManageActivity;
import com.lkjuhkmnop.textquest.tqmanager.TQManager;

public class Tools {
//    To use in intents when starting QuestManageActivity (to put extra with action type)
    public static final String QUEST_MANAGE_ACTION_EXTRA_NAME = "action";
//    Action types for QuestManageActivity (to use as extra's in intents)
    public static final int QUEST_MANAGE_ACTION_ADD_QUEST = 1;
    public static final int QUEST_MANAGE_ACTION_REDACT_QUEST = 2;

    private static final TQManager TQM = new TQManager();

    public static TQManager getTqManager() {
        return TQM;
    }

    public static void startGamesActivity(Context packageContext, View viewToRevealFrom) {
//            Build an options Bundle
        Bundle options = ActivityOptions.makeClipRevealAnimation(viewToRevealFrom, viewToRevealFrom.getWidth()/2, viewToRevealFrom.getHeight()/2, viewToRevealFrom.getWidth()/4, viewToRevealFrom.getHeight()/4).toBundle();
//            Create intent
        Intent intent = new Intent(packageContext, GamesActivity.class);
//            Start the activity specified in the intent (GamesActivity) with options
        packageContext.startActivity(intent, options);
    }

    public static void startLibraryActivity(Context packageContext, View viewToRevealFrom) {
//            Build an options Bundle
        Bundle options = ActivityOptions.makeClipRevealAnimation(viewToRevealFrom, viewToRevealFrom.getWidth()/2, viewToRevealFrom.getHeight()/2, viewToRevealFrom.getWidth()/4, viewToRevealFrom.getHeight()/4).toBundle();
//            Create intent
        Intent intent = new Intent(packageContext, LibraryActivity.class);
//            Start the activity specified in the intent (LibraryActivity) with options
        packageContext.startActivity(intent, options);
    }

    public static void startQuestManageActivityToAddQuest(Context packageContext, View viewToRevealFrom) {
//            Build an options Bundle
        Bundle options = ActivityOptions.makeClipRevealAnimation(viewToRevealFrom, viewToRevealFrom.getWidth()/2, viewToRevealFrom.getHeight()/2, viewToRevealFrom.getWidth()/4, viewToRevealFrom.getHeight()/4).toBundle();
//            Create intent
        Intent intent = new Intent(packageContext, QuestManageActivity.class);
        intent.putExtra(Tools.QUEST_MANAGE_ACTION_EXTRA_NAME, Tools.QUEST_MANAGE_ACTION_ADD_QUEST);
//            Start the activity specified in the intent (QuestManageActivity) with options
        packageContext.startActivity(intent, options);
    }
}
