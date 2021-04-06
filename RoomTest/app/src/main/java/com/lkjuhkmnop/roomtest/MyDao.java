package com.lkjuhkmnop.roomtest;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MyDao {
    @Query("SELECT * FROM myentity")
    List<MyEntity> getAll();

    @Query("SELECT * FROM myentity WHERE id IN (:entityIds)")
    List<MyEntity> getAllByIds(int... entityIds);

    @Insert
    void insertAll(MyEntity... entices);

    @Delete
    void delete(MyEntity entity);
}
