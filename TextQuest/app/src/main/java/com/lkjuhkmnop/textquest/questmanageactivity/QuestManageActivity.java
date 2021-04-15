package com.lkjuhkmnop.textquest.questmanageactivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.lkjuhkmnop.textquest.R;
import com.lkjuhkmnop.textquest.tools.Tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Objects;

public class QuestManageActivity extends AppCompatActivity implements CharPropDialog.CharPropDialogListener {
    private int action;

    private EditText questTitle, questAuthor;
    private Button questJsonFile;
    private ImageView questAddCharProperty, questAddCharParameter;
    private RecyclerView questCharPropertiesRecView, questCharParametersRecView;
    private CharPropertiesAdapter charPropertiesAdapter;
//    private TextView tw;

    private static final int PICK_JSON_FILE_CODE = 1;

    private static final String ADD_CHAR_PROP_DIALOG_TAG = "ADD_CHAR_PROP_DIALOG_TAG";

    private String questJson;
    private LinkedHashMap<String, String> questCharacterProperties = new LinkedHashMap<>();
    private LinkedList<String[]> questCharacterParameters = new LinkedList<>();

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
        charPropertiesAdapter = new CharPropertiesAdapter(this, questCharacterProperties);
        questCharPropertiesRecView.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public void onItemsChanged(@NonNull RecyclerView recyclerView) {
                super.onItemsChanged(recyclerView);
                recyclerView.swapAdapter(charPropertiesAdapter, false);
            }
        });
        questCharPropertiesRecView.setAdapter(charPropertiesAdapter);

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
//        Show a dialog to get prop. name
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

    public void reloadCharPropsRecView() {
        charPropertiesAdapter.notifyDataSetChanged();
//        questCharPropertiesRecView.
//        questCharPropertiesRecView.requestLayout();
//        questCharPropertiesRecView.
//        questCharPropertiesRecView.setAdapter(charPropertiesAdapter);
    }

//    Add character property with specified name
    @Override
    public void onCharPropDialogPositiveClick(String charPropAddName) {
        if (questCharacterProperties.containsKey(charPropAddName)) {
            Toast.makeText(this, getString(R.string.char_prop_add_have_such_name), Toast.LENGTH_LONG).show();
        } else {
            questCharacterProperties.put(charPropAddName, "");
            reloadCharPropsRecView();
        }
//        RecyclerView.ViewHolder addedItemViewHolder = questCharPropertiesRecView.findViewHolderForAdapterPosition(0);
//        Log.d("dialog", "end");
//        addedItemViewHolder.requestValueFocus();
    }

//    User cancel adding a new char. prop.
    @Override
    public void onCharPropDialogNegativeClick() {
        Toast.makeText(this, getText(R.string.char_prop_add_cancel_msg), Toast.LENGTH_SHORT).show();

    }
}