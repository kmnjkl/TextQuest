package com.lkjuhkmnop.roomtest;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.text);

        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "mydb")
                .allowMainThreadQueries()
                .build();
        MyDao dao = db.myDao();

        MyEntity entity = new MyEntity();
        entity.title = "E 2";
        entity.items = "E 2 Items";
        dao.insertAll(entity);

        Iterator iterator = dao.getAll().iterator();
        StringBuilder sb = new StringBuilder();
        while (iterator.hasNext()) {
            sb.append(iterator.next());
        }
        text.setText(sb.toString());
        db.close();
    }
}