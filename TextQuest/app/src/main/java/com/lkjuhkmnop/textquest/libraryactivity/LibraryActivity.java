package com.lkjuhkmnop.textquest.libraryactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.lkjuhkmnop.textquest.R;
import com.lkjuhkmnop.textquest.tools.Tools;
import com.lkjuhkmnop.textquest.tqmanager.DBQuest;

public class LibraryActivity extends AppCompatActivity {
    RecyclerView libRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_TextQuest);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        libRecyclerView = findViewById(R.id.lib_recycler_view);

        DBQuest[] quests = Tools.getTqManager().getQuestsArray(getApplicationContext());
        LibraryAdapter libraryAdapter = new LibraryAdapter(quests);
        libRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        libRecyclerView.setAdapter(libraryAdapter);
    }
}