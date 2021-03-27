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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playBtn = findViewById(R.id.play_btn);
        addBtn = findViewById(R.id.add_btn);
        libBtn = findViewById(R.id.lib_btn);

        playBtn.setOnClickListener(this);
        addBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == playBtn.getId()) {
//            Build an options Bundle
            Bundle options = ActivityOptions.makeClipRevealAnimation(playBtn, playBtn.getWidth()/2, playBtn.getHeight()/2, playBtn.getWidth()/4, playBtn.getHeight()/4).toBundle();
//            Create intent
            Intent intent = new Intent(this, GamesActivity.class);
//            Start the activity specified in the intent (GamesActivity) with options
            startActivity(intent, options);
        } else if (v.getId() == addBtn.getId()) {
//            Build an options Bundle
            Bundle options = ActivityOptions.makeClipRevealAnimation(addBtn, addBtn.getWidth()/2, addBtn.getHeight()/2, addBtn.getWidth()/4, addBtn.getHeight()/4).toBundle();
//            Create intent
            Intent intent = new Intent(this, AddQuestActivity.class);
//            Start the activity specified in the intent (GamesActivity) with options
            startActivity(intent, options);
        }
    }
}