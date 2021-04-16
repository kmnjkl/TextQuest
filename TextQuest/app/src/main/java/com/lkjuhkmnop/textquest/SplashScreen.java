package com.lkjuhkmnop.textquest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.lkjuhkmnop.textquest.mainactivity.MainActivity;
import com.lkjuhkmnop.textquest.tools.PopupsManager;
import com.lkjuhkmnop.textquest.tools.Tools;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}