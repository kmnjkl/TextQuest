package com.lkjuhkmnop.textquest.tqlibactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.lkjuhkmnop.textquest.R;

public class TQLibActivity extends AppCompatActivity {
    private RecyclerView tqlibRecView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_TextQuest);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tqlib);

        tqlibRecView = findViewById(R.id.tqlib_recycler_view);


    }
}