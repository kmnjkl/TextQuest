package com.lkjuhkmnop.textquest.libraryactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.lkjuhkmnop.textquest.R;
import com.lkjuhkmnop.textquest.tools.Tools;
import com.lkjuhkmnop.textquest.tqmanager.DBQuest;

public class LibraryActivity extends AppCompatActivity {
    private RecyclerView libRecyclerView;

    private static DBQuest[] quests;
    private static Context applicationContext;
    private static LibraryAdapter libraryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_TextQuest);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        applicationContext = getApplicationContext();

        libRecyclerView = findViewById(R.id.lib_recycler_view);

        try {
            quests = Tools.getTqManager().getQuestsArray(applicationContext);
            libraryAdapter = new LibraryAdapter(applicationContext, this, quests);
            libRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            libRecyclerView.setAdapter(libraryAdapter);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void reloadQuestsArray() throws InterruptedException {
        libraryAdapter.setQuests(Tools.getTqManager().getQuestsArray(applicationContext));
        libraryAdapter.notifyDataSetChanged();
    }
}