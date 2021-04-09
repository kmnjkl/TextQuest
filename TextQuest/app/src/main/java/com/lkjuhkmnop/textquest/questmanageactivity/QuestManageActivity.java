package com.lkjuhkmnop.textquest.questmanageactivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.lkjuhkmnop.textquest.R;
import com.lkjuhkmnop.textquest.tools.Tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;

public class QuestManageActivity extends AppCompatActivity implements CharPropDialog.CharPropDialogListener {
    private int action;

    private EditText questTitle, questAuthor;
    private Button questJsonFile;
    private ImageView questAddCharProperty, questAddCharParameter;
    private RecyclerView questCharPropertiesRecView, questCharParametersRecView;
//    private TextView tw;

    private static final int PICK_JSON_FILE_CODE = 1;

    private static final String ADD_CHAR_PROP_DIALOG_TAG = "ADD_CHAR_PROP_DIALOG_TAG";

    private String questJson;
    private LinkedList<String[]> questCharacterProperties, questCharacterParameters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_TextQuest);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_manage);

        questTitle = (EditText) findViewById(R.id.quest_title);
        questAuthor = (EditText) findViewById(R.id.quest_author);
        questJsonFile = (Button) findViewById(R.id.quest_json_file_button);
        questAddCharProperty = (ImageView) findViewById(R.id.quest_add_character_property);
        questAddCharParameter = (ImageView) findViewById(R.id.quest_add_character_parameter);
        questCharPropertiesRecView = (RecyclerView) findViewById(R.id.quest_character_properties_recycler_view);
        questCharParametersRecView = (RecyclerView) findViewById(R.id.quest_character_parameters_recycler_view);
//        tw = findViewById(R.id.textView);

//        Set layout manager and adapter for character properties RecycleView
        CharPropertiesAdapter charPropertiesAdapter = new CharPropertiesAdapter(questCharacterProperties);
        questCharPropertiesRecView.setLayoutManager(new LinearLayoutManager(this));
        questCharParametersRecView.setAdapter(charPropertiesAdapter);

//        Action type (add new quest / manage existing quest settings)
//        Received as extra in intent
        action = getIntent().getIntExtra(Tools.QUEST_MANAGE_ACTION_EXTRA_NAME, Tools.QUEST_MANAGE_ACTION_ADD_QUEST);

//        Choose json file button
        questJsonFile.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("application/json");
            startActivityForResult(intent, PICK_JSON_FILE_CODE);
        });

//        User clicked on add character property
//        Add item to RecyclerView and add element to questCharacterProperties HashMap
        questAddCharProperty.setOnClickListener(v -> {
            CharPropDialog charPropDialog = new CharPropDialog();
            charPropDialog.show(getSupportFragmentManager(), ADD_CHAR_PROP_DIALOG_TAG);
        });
    }

//    User choose json file
//    Read file and save json in questJson field
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_JSON_FILE_CODE && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            StringBuilder stringBuilder = new StringBuilder();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)));
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
            } catch (IOException ignored) {}
            questJson = stringBuilder.toString();
//            tw.setText(questJson);
        }
    }

    @Override
    public void onCharPropDialogPositiveClick(String charPropAddName) {
        questCharacterProperties.add(new String[]{charPropAddName, ""});
    }

    @Override
    public void onCharPropDialogNegativeClick() {

    }
}