package com.lkjuhkmnop.textquest.playactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;
import com.lkjuhkmnop.textquest.R;
import com.lkjuhkmnop.textquest.story.TQStory;
import com.lkjuhkmnop.textquest.tools.Tools;

import java.util.HashMap;

public class PlayActivity extends AppCompatActivity {
    private RecyclerView charParamsRecView;

    public static final String GAME_ID_EXTRA_NAME = "GAME_ID_EXTRA_NAME";

    private int gameId;
    private TQStory story;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        charParamsRecView = (RecyclerView) findViewById(R.id.play_activity_char_params_rec_view);

        gameId = getIntent().getIntExtra(GAME_ID_EXTRA_NAME, 0);

        charParams = Tools.getGson().fromJson(charParamsJson, charParams.getClass());
        CharParamsRecViewAdapter charParamsRecViewAdapter = new CharParamsRecViewAdapter(charParams);
    }
}