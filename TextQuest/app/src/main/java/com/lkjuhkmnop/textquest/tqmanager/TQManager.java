package com.lkjuhkmnop.textquest.tqmanager;

import android.content.Context;

import androidx.room.Room;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lkjuhkmnop.textquest.story.TQCharacter;
import com.lkjuhkmnop.textquest.story.TQQuest;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Manage local quests library
 */
public class TQManager extends Thread {
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

    @Override
    public void run() {
        super.run();
    }

    /**
     * Method adds new quest to the local library.
     * Method gets title, author and JSON file from Twine (twison) and write all information to the database.
     */
    public String addQuest(String title, String author, HashMap<String, String> characterProperties, HashMap<String, String> characterParameters, File twineJsonFile) throws IOException {
//            Read JSON file from Twine (twison)
        Scanner fileScanner = new Scanner(twineJsonFile);
        StringBuilder fileContent = new StringBuilder();
        while (fileScanner.hasNext()) {
            fileContent.append(fileScanner.next() + " ");
        }
        fileScanner.close();
//            Replace bad attribute "creator-version" (you can't use '-' in fields names in Java) with "creator_version"
        String correctedContent = fileContent.toString().replaceAll("\"creator-version\":", "\"creator_version\":");
//            Print content with replacement to file
        PrintWriter printWriter = new PrintWriter(twineJsonFile);
        printWriter.print(correctedContent);
        printWriter.close();
//            Add "title" and "author" attributes to JSON and write to new file
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(MapperFeature.AUTO_DETECT_CREATORS, false);
        mapper.configure(MapperFeature.AUTO_DETECT_GETTERS, false);
        mapper.configure(MapperFeature.AUTO_DETECT_SETTERS, false);
//        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        TQQuest jsonObject = mapper.readValue(twineJsonFile, TQQuest.class);
        jsonObject.title = title;
        jsonObject.author = author;
        jsonObject.character = new TQCharacter(characterProperties, characterParameters);
        File questJsonFile = new File("C:\\Java\\Projects\\TextQuest\\quests\\TQ_"+title.hashCode()+".json");
//            If file with such name already exists, add '0' to the and of the file's name
        while (questJsonFile.exists()) {
            questJsonFile = new File(questJsonFile.getPath().replaceAll(".json", "0.json"));
        }
        mapper.writeValue(questJsonFile, jsonObject);
        return questJsonFile.getPath();
    }

    /**
     * Reads a TQ json file
     * (it has attributes "title", "author" and "character")
     * */
    public TQQuest getQuest(String tqFilePath) throws IOException {
//        Read a TQ json file
//        (it has attributes "title", "author" and "character")
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(MapperFeature.AUTO_DETECT_CREATORS, false);
        mapper.configure(MapperFeature.AUTO_DETECT_GETTERS, false);
        mapper.configure(MapperFeature.AUTO_DETECT_SETTERS, false);
        return mapper.readValue(new File(tqFilePath), TQQuest.class);
    }

    /**
     * getGames() returns an array of TQGame instances with information about started games
     * */
    public TQGame[] getGames(Context context) {
//        Get 'tqgame' table's DAO
        TQGameDao gameDao = getAppDatabaseInstance(context).gameDao();
//        Get all games from database table
        List<TQGame> gamesList = gameDao.getAllGames();
//        Cast gamesList to TQGame[] and return
        return (TQGame[]) gamesList.toArray();
    }

}
