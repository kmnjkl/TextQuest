package com.lkjuhkmnop.textquest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView playBtn, addBtn, libBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_TextQuest);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playBtn = findViewById(R.id.play_btn);
        addBtn = findViewById(R.id.add_btn);
        libBtn = findViewById(R.id.lib_btn);

        playBtn.setOnClickListener(this);
        addBtn.setOnClickListener(this);
        libBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == playBtn.getId()) {
//            Build an options Bundle
            Bundle options = ActivityOptions.makeClipRevealAnimation(v, v.getWidth()/2, v.getHeight()/2, v.getWidth()/4, v.getHeight()/4).toBundle();
//            Create intent
            Intent intent = new Intent(this, GamesActivity.class);
//            Start the activity specified in the intent (GamesActivity) with options
            startActivity(intent, options);
        } else if (v.getId() == addBtn.getId()) {
//            Build an options Bundle
            Bundle options = ActivityOptions.makeClipRevealAnimation(v, v.getWidth()/2, v.getHeight()/2, v.getWidth()/4, v.getHeight()/4).toBundle();
//            Create intent
            Intent intent = new Intent(this, AddQuestActivity.class);
//            Start the activity specified in the intent (GamesActivity) with options
            startActivity(intent, options);
        } else if (v.getId() == libBtn.getId()) {
//            Build an options Bundle
            Bundle options = ActivityOptions.makeClipRevealAnimation(v, v.getWidth()/2, v.getHeight()/2, v.getWidth()/4, v.getHeight()/4).toBundle();
//            Create intent
            Intent intent = new Intent(this, LibraryActivity.class);
//            Start the activity specified in the intent (GamesActivity) with options
            startActivity(intent, options);
        }
    }
}