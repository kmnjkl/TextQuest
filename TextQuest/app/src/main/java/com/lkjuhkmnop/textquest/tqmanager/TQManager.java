package com.lkjuhkmnop.textquest.tqmanager;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;

import androidx.room.Room;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lkjuhkmnop.textquest.story.TQQuest;
import com.lkjuhkmnop.textquest.story.TQStory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Class to manage local quests library.
 */
public class TQManager {
//    /===== singleton pattern implementation to use AppDatabase to work with app's database using Room =====\
    private static final String DATABASE_NAME = "textquest";
    private static volatile AppDatabase APP_DATABASE_INSTANCE;

    private static AppDatabase getAppDatabaseInstance(Context context) {
        if (APP_DATABASE_INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (APP_DATABASE_INSTANCE == null) {
                    APP_DATABASE_INSTANCE = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME).build();
                }
            }
        }
        return APP_DATABASE_INSTANCE;
    }
//    \===== singleton pattern implementation to use AppDatabase to work with app's database using Room =====/


    /**
     * Class to add a new quest to the local library in new thread.
     * @see TQManager#
     * */
    private static class AddQuest extends Thread {
//        Title of new quest
        private String title;
//        Quest's author
        private String author;
//        Character's properties and parameters in the new quest
        private HashMap<String, String> characterProperties;
        private HashMap<String, String> characterParameters;
//        Uri of json file from Twine (twison) with quest
        private Uri twineJsonFileUri;
//        Activity from which TQManager invoked
        private Activity activity;
        private ContentResolver contentResolver;

        /**
         * Constructor to set quest's parameters.
         * @see TQManager#
         * */
        public AddQuest(String title, String author, HashMap<String, String> characterProperties, HashMap<String, String> characterParameters, Uri twineJsonFileUri, Activity activity, ContentResolver contentResolver) {
            this.title = title;
            this.author = author;
            this.characterProperties = characterProperties;
            this.characterParameters = characterParameters;
            this.twineJsonFileUri = twineJsonFileUri;
            this.activity = activity;
            this.contentResolver = contentResolver;
        }

        /**
         * When thread is started, add quest with parameters specified using {@link AddQuest} class's constructor {@link AddQuest#AddQuest(String, String, HashMap, HashMap, Uri, Activity, ContentResolver)}
         * */
        @Override
        public void run() {
            super.run();
            StringBuilder fileContent = new StringBuilder();
            InputStream inputStream = null;
            try {
                inputStream = contentResolver.openInputStream(twineJsonFileUri);

                BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)));
                String line;
                while (true) {
                    try {
                        if ((line = reader.readLine()) == null) {
                            break;
                        }
                        fileContent.append(line);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    inputStream.close();
                    reader.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
//            Replace bad attribute "creator-version" (you can't use '-' in fields names in Java) with "creator_version"
                String correctedContent = fileContent.toString().replaceAll("\"creator-version\":", "\"creator_version\":");
                DBQuestDao questDao = getAppDatabaseInstance(activity).questDao();
                questDao.insertAll(new DBQuest(title, author, correctedContent));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * Method to add a new quest to the local library.
     * It uses {@link TQManager}'s inner class {@link AddQuest} to set parameters of the new quest (title, author, etc.) and start a new thread to use app's database (using Room).
     * */
    public void addQuest(String title, String author, HashMap<String, String> characterProperties, HashMap<String, String> characterParameters, Uri twineJsonFileUri, Activity activity, ContentResolver contentResolver) {
        AddQuest aq = new AddQuest(title, author, characterProperties, characterParameters, twineJsonFileUri, activity, contentResolver);
        aq.start();
    }


    /**
     * Class to get story (quest) by id from app's database.
     * @see TQManager#getQuestStory(Context, int) 
     * */
    private static class GetQuestStory extends Thread {
        private Context context;
        private int questId;
        private String questTitle;
        private String questAuthor;
        private String questJson;
        private TQStory resStory;

        public GetQuestStory(Context context, int questId) {
            this.context = context;
            this.questId = questId;
        }

        @Override
        public void run() {
            super.run();
            DBQuestDao questDao = getAppDatabaseInstance(context).questDao();
            DBQuest dbQuest = questDao.getQuestById(questId);
            String title = dbQuest.getQuestTitle();
            String author = dbQuest.getQuestAuthor();
            String json = dbQuest.getQuestJson();
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(MapperFeature.AUTO_DETECT_CREATORS, false);
            mapper.configure(MapperFeature.AUTO_DETECT_GETTERS, false);
            mapper.configure(MapperFeature.AUTO_DETECT_SETTERS, false);
            try {
                TQQuest tqQuest = mapper.readValue(json, TQQuest.class);
                resStory = new TQStory(title, author, tqQuest);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * Returns story (quest) by quest_id from app's database.
     * @see GetQuestStory
     * */
    public TQStory getQuestStory(Context context, int questId) {
        GetQuestStory questStory = new GetQuestStory(context, questId);
        return questStory.resStory;
    }


    /**
     * Class to get all started games form app's database in new thread (using Room).
     * @see TQManager#getGames(Context) 
     * */
    private static class GetGames extends Thread {
        /**
         * Context is needed to use Room.
         * @see GetGames#GetGames(Context)
         * @see TQManager#getAppDatabaseInstance(Context)
         * */
        private Context context;
        /**
         * List to save games from app's database and to have access to this information from {@link TQManager#getGames(Context)} method.
         * @see GetGames#run()
         * @see TQManager#getGames(Context)
         * */
        private List<DBGame> gamesList;

        /**
         * Constructor to set the context.
         * @see GetGames#context
         * @see TQManager#getGames(Context)
         * */
        public GetGames(Context context) {
            this.context = context;
        }

        /**
         * When thread is started, get games from app's database and save result to {@link GetGames#gamesList} field.
         * @see TQManager#getGames(Context)
         * */
        @Override
        public void run() {
            super.run();
//        Get 'tqgame' table's DAO
            DBGameDao gameDao = getAppDatabaseInstance(context).gameDao();
//        Get all games from database table and save result to GetGames.gamesList
            this.gamesList = gameDao.getAllGames();
        }
    }
    /**
     * Returns an array of TQGame instances with information about started games.
     * It uses {@link TQManager}'s inner class {@link GetGames} to set the context to use Room and start a new thread to use app's database (using Room).
     * @see GetQuestStory
     * */
    public DBGame[] getGames(Context context) {
        GetGames gg = new GetGames(context);
        gg.start();
        return gg.gamesList == null ? null : (DBGame[]) gg.gamesList.toArray();
    }

    /**
     * Class to get an array of all quests (without json) from app's database in new thread.
     * @see TQManager#getQuestsArray(Context)
     * */
    private static class GetQuestsArray extends Thread {
        /**
         * Context is needed to use Room.
         * @see GetQuestsArray#GetQuestsArray(Context)
         * @see TQManager#getAppDatabaseInstance(Context)
         * */
        private final Context context;
        private DBQuest[] resQuestsArray;

        public GetQuestsArray(Context context) {
            this.context = context;
        }

        @Override
        public void run() {
            super.run();
            DBQuestDao questDao = getAppDatabaseInstance(context).questDao();
            resQuestsArray = questDao.getAllQuestsArray();
        }
    }
    /**
     * Returns an array of all quests from app's database (without json). Method uses {@link GetQuestsArray} to access the database in new thread.
     * @see GetQuestsArray
     * */
    public DBQuest[] getQuestsArray(Context context) {
        GetQuestsArray gqa = new GetQuestsArray(context);
        gqa.start();
        return gqa.resQuestsArray;
    }

}
