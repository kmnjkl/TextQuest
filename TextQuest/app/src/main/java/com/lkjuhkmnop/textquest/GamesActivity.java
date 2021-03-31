package com.lkjuhkmnop.textquest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.lkjuhkmnop.textquest.tqmanager.TQGame;
import com.lkjuhkmnop.textquest.tqmanager.TQManager;

import static com.lkjuhkmnop.textquest.tqmanager.TQManager.getGames;

public class GamesActivity extends AppCompatActivity {
    private RecyclerView gamesRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);

        gamesRV = findViewById(R.id.games_recycler_view);

        TQGame[] games = TQManager.getGames();
        GamesAdapter gamesAdapter = new GamesAdapter(games);
        gamesRV.setLayoutManager(new LinearLayoutManager(this));
        gamesRV.setAdapter(gamesAdapter);
    }
}