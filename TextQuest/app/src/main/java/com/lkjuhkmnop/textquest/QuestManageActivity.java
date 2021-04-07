package com.lkjuhkmnop.textquest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.lkjuhkmnop.textquest.tools.Tools;

public class QuestManageActivity extends AppCompatActivity {
    private int action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_manage);

        action = getIntent().getIntExtra(Tools.QUEST_MANAGE_ACTION_EXTRA_NAME, Tools.QUEST_MANAGE_ACTION_ADD_QUEST);
    }
}