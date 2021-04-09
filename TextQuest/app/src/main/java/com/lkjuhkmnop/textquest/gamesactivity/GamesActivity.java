package com.lkjuhkmnop.textquest.gamesactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.lkjuhkmnop.textquest.R;
import com.lkjuhkmnop.textquest.tools.Tools;
import com.lkjuhkmnop.textquest.tqmanager.DBGame;

public class GamesActivity extends AppCompatActivity {
    private RecyclerView gamesRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_TextQuest);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);

        gamesRV = findViewById(R.id.games_recycler_view);

        DBGame[] games = Tools.getTqManager().getGames(getApplicationContext());
        GamesAdapter gamesAdapter = new GamesAdapter(games);
        gamesRV.setLayoutManager(new LinearLayoutManager(this));
        gamesRV.setAdapter(gamesAdapter);
    }
}