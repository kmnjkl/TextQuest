package com.lkjuhkmnop.textquest.playactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lkjuhkmnop.textquest.R;
import com.lkjuhkmnop.textquest.story.TQStory;
import com.lkjuhkmnop.textquest.tools.Tools;
import com.lkjuhkmnop.textquest.tqmanager.DBGame;
import com.lkjuhkmnop.textquest.tqmanager.DBQuest;

public class PlayActivity extends AppCompatActivity {
    private RecyclerView charParamsRecView, linksRecView;

    public static final String GAME_NAME_EXTRA_NAME = "GAME_NAME_EXTRA_NAME";

    private String gameTitle;
    private TQStory story;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        charParamsRecView = (RecyclerView) findViewById(R.id.play_activity_char_params_rec_view);

        gameTitle = getIntent().getStringExtra(GAME_NAME_EXTRA_NAME);
        try {
            story = Tools.getTqManager().getStoryByGameTitle(getApplicationContext(), gameTitle);
        } catch (InterruptedException | JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}