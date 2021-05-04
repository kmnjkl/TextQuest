package com.lkjuhkmnop.textquest.mainactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.lkjuhkmnop.textquest.R;
import com.lkjuhkmnop.textquest.tools.Tools;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView playBtn, addBtn, libBtn;
    private int popupCount=0;

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
            Tools.startGamesActivity(this, v);
        } else if (v.getId() == addBtn.getId()) {
            Tools.startQuestManageActivityToAddQuest(this, v);
        } else if (v.getId() == libBtn.getId()) {
            Tools.startLibraryActivity(this, v);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}