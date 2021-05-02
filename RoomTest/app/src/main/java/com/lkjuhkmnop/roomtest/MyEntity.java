package com.lkjuhkmnop.roomtest;


import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class MyEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String title;

    public String items;

    @Ignore
    @Override
    public String toString() {
        return "MyEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", items='" + items + '\'' +
                '}';
    }
}
