package com.lkjuhkmnop.textquest.tools;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lkjuhkmnop.textquest.gamesactivity.GamesActivity;
import com.lkjuhkmnop.textquest.libraryactivity.LibraryActivity;
import com.lkjuhkmnop.textquest.questmanageactivity.QuestManageActivity;
import com.lkjuhkmnop.textquest.tqmanager.TQManager;

public class Tools {
    private static PopupsManager popupsManager;
    public static PopupsManager getPopupsManager() {
        if (popupsManager == null) {
            synchronized (PopupsManager.class) {
                if (popupsManager == null) {
                    popupsManager = new PopupsManager();
                    popupsManager.start();
                }
            }
        }
        return popupsManager;
    }

    private static volatile TQManager TQM;
    public static TQManager getTqManager() {
        if (TQM == null) {
            synchronized (TQManager.class) {
                if (TQM == null) {
                    TQM = new TQManager();
                }
            }
        }
        return TQM;
    }


    private static volatile ObjectMapper mapper;
    public static ObjectMapper getMapper() {
        if (mapper == null) {
            synchronized (mapper) {
                if (mapper == null) {
                    mapper = new ObjectMapper();
                    mapper.configure(MapperFeature.AUTO_DETECT_CREATORS, false);
                    mapper.configure(MapperFeature.AUTO_DETECT_GETTERS, false);
                    mapper.configure(MapperFeature.AUTO_DETECT_SETTERS, false);
                }
            }
        }
        return mapper;
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
        intent.putExtra(QuestManageActivity.ACTION_EXTRA_NAME, QuestManageActivity.ACTION_ADD_QUEST);
//            Start the activity specified in the intent (QuestManageActivity) with options
        packageContext.startActivity(intent, options);
    }
}
