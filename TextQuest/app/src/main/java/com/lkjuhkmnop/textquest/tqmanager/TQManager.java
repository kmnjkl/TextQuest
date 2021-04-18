package com.lkjuhkmnop.textquest.tqmanager;

import android.content.ContentResolver;
import android.content.Context;

import androidx.room.Room;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lkjuhkmnop.textquest.story.TQCharacter;
import com.lkjuhkmnop.textquest.story.TQQuest;
import com.lkjuhkmnop.textquest.story.TQStory;

import java.util.HashMap;
import java.util.List;

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
     * @see TQManager#addQuest
     * */
    private static class AddQuest extends Thread {
//        Title of new quest
        private String title;
//        Quest's author
        private String author;
//        Character's properties and parameters in the new quest
        private HashMap<String, String> characterProperties;
        private HashMap<String, String> characterParameters;
//        Json from Twine (twison) with quest
        private String twineJson;
//        Activity from which TQManager invoked
        private Context context;
        private ContentResolver contentResolver;

        /**
         * Constructor to set quest's parameters.
         * @see TQManager#addQuest
         * */
        public AddQuest(String title, String author, HashMap<String, String> characterProperties, HashMap<String, String> characterParameters, String twineJson, Context context, ContentResolver contentResolver) {
            this.title = title;
            this.author = author;
            this.characterProperties = characterProperties;
            this.characterParameters = characterParameters;
            this.twineJson = twineJson;
            this.context = context;
            this.contentResolver = contentResolver;
        }

        /**
         * When thread is started, add quest with parameters specified using {@link AddQuest} class's constructor {@link AddQuest#AddQuest}
         * */
        @Override
        public void run() {
            super.run();
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(MapperFeature.AUTO_DETECT_CREATORS, false);
            mapper.configure(MapperFeature.AUTO_DETECT_GETTERS, false);
            mapper.configure(MapperFeature.AUTO_DETECT_SETTERS, false);
//            Replace bad attribute "creator-version" (you can't use '-' in fields names in Java) with "creator_version"
            String correctedJson = twineJson.replaceAll("\"creator-version\":", "\"creator_version\":");
            DBQuestDao questDao = getAppDatabaseInstance(context).questDao();
            try {
                questDao.insertAll(new DBQuest(title, author, mapper.writeValueAsString(characterProperties), mapper.writeValueAsString(characterParameters), correctedJson));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * Method to add a new quest to the local library.
     * It uses {@link TQManager}'s inner class {@link AddQuest} to set parameters of the new quest (title, author, etc.) and start a new thread to use app's database (using Room).
     * */
    public void addQuest(String title, String author, HashMap<String, String> characterProperties, HashMap<String, String> characterParameters, String twineJson, Context context, ContentResolver contentResolver) {
        AddQuest aq = new AddQuest(title, author, characterProperties, characterParameters, twineJson, context, contentResolver);
        aq.start();
    }


    /**
     * Class to get story (quest) by id from app's database.
     * @see TQManager#getQuestStory(Context, int) 
     * */
    private static class GetQuestStory extends Thread {
        private Context context;
        private int questId;
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
            String characterProperties = dbQuest.getCharacterProperties();
            String characterParameters = dbQuest.getCharacterParameters();
            String json = dbQuest.getQuestJson();
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(MapperFeature.AUTO_DETECT_CREATORS, false);
            mapper.configure(MapperFeature.AUTO_DETECT_GETTERS, false);
            mapper.configure(MapperFeature.AUTO_DETECT_SETTERS, false);
            try {
                TQQuest tqQuest = mapper.readValue(json, TQQuest.class);
                resStory = new TQStory(title, author, new TQCharacter(characterProperties, characterParameters), tqQuest);
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
     * @see TQManager#getQuestsArray
     * */
    private static class GetQuestsArray extends Thread {
        /**
         * Context is needed to use Room.
         * @see #GetQuestsArray
         * @see #run()
         * @see TQManager#getAppDatabaseInstance
         * */
        private final Context context;
        /**
         * Array to save query results.
         * @see
         * */
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
    public DBQuest[] getQuestsArray(Context context) throws InterruptedException {
        GetQuestsArray gqa = new GetQuestsArray(context);
        gqa.start();
        gqa.join();
        return gqa.resQuestsArray;
    }


    /**
     * Class to delete a quest from the app's database in new thread.
     * @see #deleteQuestById
     * */
    private static class DeleteQuestById extends Thread {
        /**
         * Context is needed to use Room.
         * @see #DeleteQuestById
         * @see #run()
         * @see TQManager#getAppDatabaseInstance
         * */
        private final Context context;
        /**
         * Id of the quest to delete.
         * @see #DeleteQuestById
         * @see #run
         * */
        private int id;

        /**
         * Constructor sets {@link #context} (needed to get the AppDatabase instance) and {@link #id} of the quest to delete.
         * @see #run
         * */
        public DeleteQuestById(Context context, int id) {
            this.context = context;
            this.id = id;
        }

        @Override
        public void run() {
            super.run();
            DBQuestDao questDao = getAppDatabaseInstance(context).questDao();
            questDao.deleteQuestsByIds(id);
        }
    }
    /**
     * Method to delete a quest from the app's database by id.
     * It uses {@link DeleteQuestById} to set the {@link DeleteQuestById#context} and the {@link DeleteQuestById#id} of the quest to delete and to start a new thread to use the app's database (using Room).
     * @see DeleteQuestById
     * */
    public void deleteQuestById(Context context, int id) throws InterruptedException {
        DeleteQuestById dqbi = new DeleteQuestById(context, id);
        dqbi.start();
        dqbi.join();
    }
}
