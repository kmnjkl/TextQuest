package com.lkjuhkmnop.textquest.playactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lkjuhkmnop.textquest.R;
import com.lkjuhkmnop.textquest.story.TQStory;
import com.lkjuhkmnop.textquest.story.TwLink;
import com.lkjuhkmnop.textquest.tools.Tools;

public class PlayActivity extends AppCompatActivity {
    private TextView textView;
    private RecyclerView charParamsRecView, linksRecView;
    private Button restartButton, closeButton;

    public static final String GAME_NAME_EXTRA_NAME = "GAME_NAME_EXTRA_NAME";

    private String gameTitle;
    private TQStory story;
    private int linkPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        textView = (TextView) findViewById(R.id.play_activity_text);
        charParamsRecView = (RecyclerView) findViewById(R.id.play_activity_char_params_rec_view);
        linksRecView = (RecyclerView) findViewById(R.id.play_activity_links_rec_view);
        restartButton = (Button) findViewById(R.id.play_restart_button);
        closeButton = (Button) findViewById(R.id.play_close_button);

        gameTitle = getIntent().getStringExtra(GAME_NAME_EXTRA_NAME);
        try {
            story = Tools.getTqManager().getStoryByGameTitle(getApplicationContext(), gameTitle);
        } catch (InterruptedException | JsonProcessingException e) {
            e.printStackTrace();
        }

        CharParamsAdapter charParamsRecViewAdapter = new CharParamsAdapter(story.getCurrentCharacterParameters());
        charParamsRecView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        charParamsRecView.setAdapter(charParamsRecViewAdapter);

        LinksAdapter linksAdapter = new LinksAdapter(this, null);
        linksRecView.setLayoutManager(new LinearLayoutManager(this));
        linksRecView.setAdapter(linksAdapter);

        boolean finish = false;
        while (!finish) {
            String text = story.processCurrentPassage();
            charParamsRecViewAdapter.notifyDataSetChanged();
            textView.setText(text);
            finish = story.isEnd();
            if (!finish) {
                linksAdapter.setData(story.getCurrentPassageLinks());
                linksAdapter.notifyDataSetChanged();
            } else {
                linksAdapter.cleanData();
                linksAdapter.notifyDataSetChanged();
                restartButton.setVisibility(View.VISIBLE);
                closeButton.setVisibility(View.VISIBLE);
                restartButton.setOnClickListener(v -> {
//                    Restart
                    story.restart();
                    restartButton.setVisibility(View.INVISIBLE);
                    closeButton.setVisibility(View.INVISIBLE);
                    this.notify();
                });
                closeButton.setOnClickListener(v -> {
//                    Close
                    finish();
                });
            }
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            story.goByLinkNumber(linkPosition+1);
        }
    }

    public int getLinkPosition() {
        return linkPosition;
    }

    public void setLinkPosition(int linkPosition) {
        this.linkPosition = linkPosition;
    }
}